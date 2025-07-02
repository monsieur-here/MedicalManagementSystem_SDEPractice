<script>
  import AuthWrapper from "$lib/components/AuthWrapper.svelte";
  import { AUTH_ROUTES } from "$lib/routes";
</script>

<AuthWrapper redirectTo={AUTH_ROUTES.login.url} let:user let:isAuthenticated>
  {#if isAuthenticated}
    <main>
      <slot />
    </main>
  {/if}
</AuthWrapper>
