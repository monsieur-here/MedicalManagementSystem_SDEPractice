<script>
  import { onMount } from "svelte";
  import { user } from "$lib/stores/auth.js";
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";
  import { AUTH_ROUTES } from "$lib/routes";
  import { browser } from "$app/environment";

  export let redirectTo = AUTH_ROUTES.login.url;
  export let requireAuth = true;
  export let showLoader = true;

  let authState;
  let mounted = false;

  $: authState = $user;

  onMount(() => {
    mounted = true;
    user.checkAuth();
  });

  // Only handle redirection on client-side after component is mounted
  $: if (
    mounted &&
    browser &&
    !authState?.loading &&
    requireAuth &&
    !authState?.isAuthenticated
  ) {
    const currentPath = $page.url.pathname;
    if (currentPath !== redirectTo) {
      goto(`${redirectTo}?redirect=${encodeURIComponent(currentPath)}`);
    }
  }

  $: shouldRenderChildren = !requireAuth || authState?.isAuthenticated;
  $: shouldShowLoader = authState?.loading && showLoader;
</script>

{#if !mounted}
  <!-- Show loading on initial server render -->
  <div class="auth-loading">
    <div class="spinner"></div>
    <p>Loading...</p>
  </div>
{:else if shouldShowLoader}
  <div class="auth-loading">
    <div class="spinner"></div>
    <p>Loading...</p>
  </div>
{:else if shouldRenderChildren}
  <slot user={authState.user} isAuthenticated={authState.isAuthenticated} />
{:else}
  <div class="auth-redirect">
    <p>Redirecting to login...</p>
  </div>
{/if}

<style>
  .auth-loading {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 200px;
  }

  .spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #3498db;
    border-radius: 50%;
    animation: spin 1s linear infinite;
  }

  @keyframes spin {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }

  .auth-redirect {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 200px;
  }
</style>
