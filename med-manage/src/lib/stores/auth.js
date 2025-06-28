import { writable } from "svelte/store";
import { browser } from "$app/environment";

// Create user store
function createUserStore() {
  const { subscribe, set, update } = writable(null);

  return {
    subscribe,
    login: async (email, password) => {
      try {
        // TODO: update it with actual api
        const response = await fetch("/api/auth/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
          throw new Error("Invalid credentials");
        }

        const userData = await response.json();

        // Store token in localStorage
        if (browser) {
          localStorage.setItem("token", userData.token);
        }

        set(userData.user);
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
