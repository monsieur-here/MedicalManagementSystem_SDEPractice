<script>
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { user } from "$lib/stores/auth.js";
  import Login from "./auth/login/+page.svelte";
  import Register from "./auth/register/+page.svelte";
  import {
    AUTH_ROUTES,
    DOCTORS_ROUTES,
    PATIENT_ROUTES,
    RECEPTIONIST_ROUTES,
  } from "$lib/routes";

  onMount(() => {
    const userData = JSON.parse(localStorage.getItem("user_data"));
    const userRoleBasedPath =
      userData?.role === "ROLE_PATIENT"
        ? PATIENT_ROUTES.dashboard
        : userData?.role === "ROLE_RECEPTIONIST"
          ? RECEPTIONIST_ROUTES.dashboard
            ? userData?.role === "ROLE_DOCTOR"
            : DOCTORS_ROUTES.dashboard
          : null;

    // Redirect if already logged in
    if (userData?.email && userRoleBasedPath) {
      goto(userRoleBasedPath?.url);
    } else {
      goto(AUTH_ROUTES.login.url);
    }
  });
</script>

<svelte:head>
  <title>Authentication</title>
</svelte:head>
