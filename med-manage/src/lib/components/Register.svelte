<!-- src/lib/components/Register.svelte -->
<script>
  import { user } from "$lib/stores/auth.js";
  import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();

  let name = "";
  let email = "";
  let password = "";
  let confirmPassword = "";
  let loading = false;
  let error = "";

  async function handleRegister() {
    if (!name || !email || !password || !confirmPassword) {
      error = "Please fill in all fields";
      return;
    }

    if (password !== confirmPassword) {
      error = "Passwords do not match";
      return;
    }

    if (password.length < 6) {
      error = "Password must be at least 6 characters";
      return;
    }

    loading = true;
    error = "";

    const result = await user.register(email, password, name);

    if (result.success) {
      dispatch("success");
    } else {
      error = result.error;
    }

    loading = false;
  }
</script>

<div class="card p-6 max-w-md mx-auto">
  <section class="p-4">
    <header class="card-header text-center">
      <h2 class="h2">Sign Up</h2>
    </header>

    <form on:submit|preventDefault={handleRegister} class="space-y-4">
      <label class="label">
        <span>Full Name</span>
        <input
          class="input"
          type="text"
          bind:value={name}
          placeholder="Enter your full name"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span>Email</span>
        <input
          class="input"
          type="email"
          bind:value={email}
          placeholder="Enter your email"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span>Password</span>
        <input
          class="input"
          type="password"
          bind:value={password}
          placeholder="Enter your password"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span>Confirm Password</span>
        <input
          class="input"
          type="password"
          bind:value={confirmPassword}
          placeholder="Confirm your password"
          disabled={loading}
        />
      </label>

      {#if error}
        <aside class="alert variant-filled-error">
          <div class="alert-message">
            <p>{error}</p>
          </div>
        </aside>
      {/if}

      <footer class="card-footer text-center">
        <p class="text-sm">
          Already have an account?
          <button class="anchor" on:click={() => dispatch("switch-mode")}>
            Login
          </button>
        </p>
      </footer>

      <button type="submit" class="login-button" disabled={loading}>
        {loading ? "Creating Account..." : "Sign Up"}
      </button>
    </form>
  </section>
</div>

<style>
  .card {
    background: var(--color-surface-100);
    border: 1px solid var(--color-surface-300);
    border-radius: var(--theme-rounded-base);
  }
  form {
    max-width: 400px;
    margin: 2rem auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  input {
    width: 94%;
    padding: 0.7rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100%;
    padding: 0.7rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #fff;
    font-size: 1rem;
  }
  .anchor {
    background-color: rgba(0, 0, 0, 0);
    color: black;
    text-decoration: underline;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  .login-button {
    width: 100%;
    padding: 0.7rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
