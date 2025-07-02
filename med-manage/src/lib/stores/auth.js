import { writable } from "svelte/store";
import { browser } from "$app/environment";

import { BASE_API_URL } from "$lib/config.js";
import { ConvertToJSONFromStream } from "$lib/utils";

// Create user store
function createUserStore() {
  const { subscribe, set, update } = writable({
    isAuthenticated: false,
    user: null,
    loading: false,
  });

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
          localStorage.setItem(
            "user_data",
            JSON.stringify(userData?.data?.user)
          );
        }

        set({
          isAuthenticated: true,
          user: userData?.data?.user,
          loading: false,
        });
        return { success: true };
      } catch (error) {
        return { success: false, error: error.message };
      }
    },

    register: async (postObj) => {
      try {
        const response = await fetch(BASE_API_URL + "/patient/signup", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(postObj),
        });

        console.log(response);

        if (!response.ok) {
          throw new Error("Registration failed");
        }

        const userData = await response.json();

        if (browser) {
          localStorage.setItem("token", userData.token);
        }

        set({
          isAuthenticated: true,
          user: userData?.data?.user,
          loading: false,
        });
        return { success: true };
      } catch (error) {
        return { success: false, error: error.message };
      }
    },

    logout: () => {
      if (browser) {
        localStorage.removeItem("user_data");
      }
      set({
        isAuthenticated: false,
        user: null,
        loading: false,
      });
    },

    checkAuth: async () => {
      if (!browser) return;

      const userData = JSON.parse(localStorage.getItem("user_data"));
      if (!userData?.email) return;

      try {
        if (userData?.email) {
          set({
            isAuthenticated: true,
            user: userData,
            loading: false,
          });
        } else {
          localStorage.removeItem("user_data");
        }
      } catch (error) {
        console.error("Auth check failed:", error);
        localStorage.removeItem("user_data");
      }
    },
  };
}

export const user = createUserStore();
