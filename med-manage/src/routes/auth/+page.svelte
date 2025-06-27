<script>
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { user } from "$lib/stores/auth.js";
  import Login from "$lib/components/Login.svelte";
  import Register from "$lib/components/Register.svelte";

  let currentMode = "login"; // 'login' or 'register'

  onMount(() => {
    // Redirect if already logged in
    if ($user) {
      goto("/dashboard");
    }
  });

  function handleSuccess() {
    // Redirect to dashboard after successful auth
    goto("/dashboard");
  }

  function switchMode() {
    currentMode = currentMode === "login" ? "register" : "login";
  }
</script>

<svelte:head>
  <title>Authentication</title>
</svelte:head>

<div
  class="min-h-screen bg-gradient-to-br from-primary-500 to-secondary-500 flex items-center justify-center p-4"
>
  <div class="w-full max-w-md">
    {#if currentMode === "login"}
      <Login on:success={handleSuccess} on:switch-mode={switchMode} />
    {:else}
      <Register on:success={handleSuccess} on:switch-mode={switchMode} />
    {/if}
  </div>
</div>
