<script>
  import { goto } from "$app/navigation";
  import {
    AUTH_ROUTES,
    DOCTORS_ROUTES,
    PATIENT_ROUTES,
    RECEPTIONIST_ROUTES,
  } from "$lib/routes";
  import { user } from "$lib/stores/auth.js";
  import { ConvertToJSONFromStream } from "$lib/utils";
  import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();

  let name = "";
  let firstName = "";
  let lastName = "";
  let email = "";
  let password = "";
  let confirmPassword = "";
  let loading = false;
  let error = "";

  async function handleRegister(e) {
    if (!firstName || !lastName || !email || !password || !confirmPassword) {
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

    const data = new FormData(e.currentTarget);

    const signUpPayload = {
      first_name: data.get("firstName"),
      last_name: data.get("lastName"),
      email: data.get("email"),
      password: data.get("password"),
      role: "ROLE_PATIENT",
    };

    const result = await user.register(signUpPayload);

    if (result?.code === 200) {
      const redirectPath =
        result?.data?.user?.role === "ROLE_PATIENT"
          ? PATIENT_ROUTES.dashboard.url
          : result?.data?.user?.role === "ROLE_RECEPTIONIST"
            ? RECEPTIONIST_ROUTES.dashboard.url
            : result?.data?.user?.role === "ROLE_DOCTOR"
              ? DOCTORS_ROUTES.dashboard.url
              : null;

      goto(redirectPath);
    } else {
      loading = false;
      error = "Please enter a valid credentials!";
    }
  }

  const gotoLogin = () => {
    goto(AUTH_ROUTES.login.url);
  };
</script>

<div
  class="min-h-screen bg-gradient-to-br from-primary-500 to-secondary-500 flex items-center justify-center p-4"
>
  <div class="w-full max-w-md">
    <div class="card p-6 max-w-md mx-auto">
      <section class="p-4">
        <header class="card-header text-center">
          <h2 class="h2">Sign Up</h2>
        </header>

        <form on:submit|preventDefault={handleRegister} class="space-y-4">
          <label class="label">
            <span>First Name</span>
            <input
              class="input"
              type="text"
              name="firstName"
              bind:value={firstName}
              placeholder="Enter your first name"
              disabled={loading}
            />
          </label>

          <label class="label">
            <span>Last Name</span>
            <input
              class="input"
              type="text"
              name="lastName"
              bind:value={lastName}
              placeholder="Enter your Last name"
              disabled={loading}
            />
          </label>

          <label class="label">
            <span>Email</span>
            <input
              class="input"
              type="email"
              name="email"
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
              name="password"
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
              name="confirmPassword"
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

          <button type="submit" class="login-button" disabled={loading}>
            {loading ? "Creating Account..." : "Sign Up"}
          </button>
        </form>

        <footer
          style="display: flex; justify-content: center;"
          class="card-footer text-center"
        >
          <p class="text-sm">
            Already have an account?
            <button class="anchor" on:click={() => gotoLogin()}> Login </button>
          </p>
        </footer>
      </section>
    </div>
  </div>
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
