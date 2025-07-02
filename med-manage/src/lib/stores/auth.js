import { writable } from "svelte/store";
import { browser } from "$app/environment";

import { BASE_API_URL } from "$lib/config.js";
import { AUTH_ROUTES } from "$lib/routes.js";
import { ConvertToJSONFromStream } from "$lib/utils";

// Create user store
function createUserStore() {
  const { subscribe, set, update } = writable(null);

  return {
    subscribe,
    login: async (email, password) => {
      try {
        const response = await fetch(BASE_API_URL + "/patient/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email, password }),
        });

        const userData = await ConvertToJSONFromStream(response);

        if (!userData.code === 200) {
          throw new Error("Invalid credentials");
        }

        // Store token in localStorage
        if (browser && userData?.data?.user?.email) {
          localStorage.setItem("token", userData?.data?.user?.email);
        }

        set(userData?.data?.user);
        return { success: true };
      } catch (error) {
        return { success: false, error: error.message };
      }
    },

    register: async (email, password, name) => {
      try {
        // TODO: update it with actual api

        const response = await fetch("/api/auth/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email, password, name }),
        });

        if (!response.ok) {
          throw new Error("Registration failed");
        }

        const userData = await response.json();

        if (browser) {
          localStorage.setItem("token", userData.token);
        }

        set(userData.user);
        return { success: true };
      } catch (error) {
        return { success: false, error: error.message };
      }
    },

    logout: () => {
      if (browser) {
        localStorage.removeItem("token");
      }
      set(null);
    },

    checkAuth: async () => {
      if (!browser) return;

      const token = localStorage.getItem("token");
      if (!token) return;

      try {
        // TODO: update it with actual api

        const response = await fetch("/api/auth/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const userData = await response.json();
          set(userData.user);
        } else {
          localStorage.removeItem("token");
        }
      } catch (error) {
        console.error("Auth check failed:", error);
        localStorage.removeItem("token");
      }
    },
  };
}

export const user = createUserStore();
