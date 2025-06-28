<script>
  import { goto } from "$app/navigation";

  let user_type = "";
  let email = "";
  let password = "";
  let error = "";
  let loggedIn = false;

  async function handleMedicalLogin(e) {
    if (!email || !password) {
      error = "Please fill in all fields.";
      return;
    }

    localStorage.setItem("user_type", user_type);

    // Mock login check
    if (
      user_type === "Patient" &&
      email === "test@example.com" &&
      password === "password"
    ) {
      loggedIn = true;
      console.log(user_type);

      goto(`/dashboard?user_type=${user_type}`);
    } else if (
      user_type === "Staff" &&
      email === "test@example.com" &&
      password === "password"
    ) {
      loggedIn = true;
      console.log(user_type);

      goto(`/homepage?user_type=${user_type}`);
    } else {
      error = "Invalid credentials.";
    }

    const data = new FormData(e.currentTarget);

    // To Do - Validate Email and Password

    const validateLogin = {
      email: data.get("email"),
      password: data.get("password"),
    };

    // Added - Sending POST to backend
    try {
      const myHeaders = new Headers();
      myHeaders.append("Content-Type", "application/json");

      //   const raw = JSON.stringify();

      const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: JSON.stringify(validateLogin),
      };

      const res = await fetch(
        "http://localhost:8080/Understanding_Integration-Backend/patient/login",
        requestOptions
      );

      console.log(res);

      if (res.ok) {
        alert("🎉 Login successfully done!");
        loggedIn = true;
        goto(`/dashboard?user_type=${user_type}`);
        // goto(DOCTOR.DASHBOARD)
      } else {
        alert("Oops, something went wrong while creating new user ...!");

        const text = await res.text();
        error = `Login failed: ${text}`;
      }
    } catch (err) {
      error = "Error connecting to server.";
      console.error(err);
    }
  }
</script>

{#if loggedIn}
  <h2 style="text-align: center;">🎉 Welcome {email}!</h2>
{:else}
  <h2 style="text-align: center;">Login</h2>

  <form on:submit|preventDefault={handleMedicalLogin}>
    <select bind:value={user_type} required>
      <option value="" disabled selected>Select User</option>
      <option>Patient</option>
      <option>Staff</option>
    </select>

    <input
      type="email"
      placeholder="Email"
      name="email"
      bind:value={email}
      required
    />
    <input
      type="password"
      placeholder="Password"
      name="password"
      bind:value={password}
      required
    />
    {#if user_type === "Patient"}
      <p style="text-align: center; margin-top: 0rem;">
        Don't have an account? <a href="/registration?user_type=${user_type}"
          >Register here</a
        >
      </p>
    {:else if user_type === "Staff"}
      <p style="text-align: center; margin-top: 0rem;">
        Don't have login Details? <a href="#">Ask admin</a>
      </p>
    {/if}

    <!-- {#if error}
            <p class="error">{error}</p>
        {/if} -->

    <button type="submit">Login</button>
  </form>
{/if}

<style>
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
  input,
  select {
    width: 100%;
    padding: 0.7rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #fff;
    font-size: 1rem;
  }
  input {
    width: 94%;
    padding: 0.7rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  button {
    width: 100%;
    padding: 0.7rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  /* p.error {
    color: red;
    margin-top: -0.5rem;
    margin-bottom: 1rem;
    text-align: center;
  } */
</style>
