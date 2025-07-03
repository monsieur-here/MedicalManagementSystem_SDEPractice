<script>
  import { onMount } from "svelte";
  import { user } from "$lib/stores/auth";
  import { AUTH_ROUTES } from "$lib/routes";

  let userData = JSON.parse(localStorage.getItem("user_data"));

  onMount(() => {
    if (!userData?.role) {
      window.location.href = AUTH_ROUTES.login.url;
    }
  });
</script>

<slot />
