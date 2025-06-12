<script>
    import { goto } from "$app/navigation";

    let user_type="";
    let email = "";
    let password = "";
    let error = "";
    let loggedIn = false;
    
    function handleLogin() {
        error = "";

        if (!email || !password) {
            error = "Please fill in all fields.";
            return;
        }
	    localStorage.setItem('user_type', user_type);
        // Mock login check
        if (user_type==="Patient" && email === "test@example.com" && password === "password") {
            loggedIn = true;
            console.log(user_type);

            goto(`/dashboard?user_type=${user_type}`);
        } 
        else if(user_type==="Staff" && email === "test@example.com" && password === "password"){
            loggedIn = true;
            console.log(user_type);

            goto(`/homepage?user_type=${user_type}`);
        }
        else {
            error = "Invalid credentials.";
        }
    }
</script>

{#if loggedIn}
    <h2 style="text-align: center;">🎉 Welcome {email}!</h2>
{:else}
    <h2 style="text-align: center;">Login</h2>

    <form on:submit|preventDefault={handleLogin}>
        <select bind:value={user_type} required>
		<option value="" disabled selected>Select User</option>
		<option>Patient</option>
		<option>Staff</option>

	</select>
        <input type="email" placeholder="Email" bind:value={email} required />
        <input
            type="password"
            placeholder="Password"
            bind:value={password}
            required
        />
        {#if user_type === "Patient"}
        <p style="text-align: center; margin-top: 0rem;">
            Don't have an account? <a href="/registration?user_type=${user_type}">Register here</a>
        </p>
        {/if}
        {#if error}
            <p class="error">{error}</p>
        {/if}

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
    input, select {
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
    p.error {
        color: red;
        margin-top: -0.5rem;
        margin-bottom: 1rem;
        text-align: center;
    }
</style>
