<script>
    import { goto } from "$app/navigation";
    import { page } from '$app/stores';

	$: user_type = $page.url.searchParams.get("user_type");    
    let name="";
    let dateOfBirth="";
    let gender="";
    let contact="";
    let address="";
    let email = "";
    let password = "";
    let error = "";
    let loggedIn = false;
    console.log(user_type);

    function handleLogin() {
	error = "";

	const requiredFieldsFilled =
		user_type === "Patient" || user_type === "$Patient"
			? name && dateOfBirth && gender && contact && address && email && password
			: name && dateOfBirth && gender && contact && address && email;

	if (!requiredFieldsFilled) {
		error = "Please fill in all fields.";
		return;
	}

	// 🔍 Date of Birth must be in the past
	const dobDate = new Date(dateOfBirth);
	const today = new Date();
	if (dobDate > today) {
		error = "Date of birth must be in the past.";
		return;
	}

	// 🔍 Gender must be 'Male', 'Female', or 'Others'
	const validGenders = ["male", "female", "others"];
	if (!validGenders.includes(gender.toLowerCase())) {
		error = "Gender must be Male, Female, or Others.";
		return;
	}

	// 🔍 German mobile number regex
	const germanMobileRegex = /^(?:\+49|0)[1-9]\d{9,13}$/;
	if (!germanMobileRegex.test(contact)) {
		error = "Please enter a valid German mobile number.";
		return;
	}
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (user_type === "Patient" || user_type === "$Patient") {
			if (!passwordRegex.test(password)) {
				error = "Password must be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number.";
				return;
			}
		}

	// ✅ If all validations pass
	alert("🎉 Registration completed successfully!");
	loggedIn = true;
	goto(user_type === "Patient" || user_type === "$Patient"
		? `/dashboard?user_type=${user_type}`
		: `/homepage?user_type=${user_type}`
	);
}
</script>

{#if loggedIn}
    <h2 style="text-align: center;">🎉 Welcome, {email}!</h2>
{:else if user_type==="Patient" || user_type==="$Patient"}
<div class="dashboard" >
    <a href="/" class="home-button">Back</a>
    <h2 style="text-align: center;">Create an Account</h2>
    <form on:submit|preventDefault={handleLogin}>
        <h4>
            Enter Full Name:
            <input type="text" placeholder="Full Name" bind:value={name} required />
        </h4>
        <h4>
            Date of Birth:
            <input type="date" placeholder="Date of Birth" bind:value={dateOfBirth} required />
        </h4>
        <h4>
            Gender:
            <input type="text" placeholder="Gender" bind:value={gender} required />
        </h4>
        <h4>
            Contact Number:
            <input type="contact" placeholder="Contact Number" bind:value={contact} required />
        </h4>
        <h4>
            Address:
            <input type="address" placeholder="Address" bind:value={address} required />
        </h4>
        <h4>
            Email ID:
            <input type="email" placeholder="Email ID" bind:value={email} required />
        </h4>
        <h4>
            Password:
            <input type="password" placeholder="Password" bind:value={password} required />
        </h4>
        {#if error}
            <p class="error">{error}</p>
        {/if}
        <button type="submit">Register</button>
    </form>
</div>
{:else}
<div class="dashboard" >
    <a href={`/homepage?user_type=${user_type}`} class="home-button">Back</a>
    <h2 style="text-align: center;">Register Patient</h2>
    <form on:submit|preventDefault={handleLogin}>
        <h4>
            Enter Full Name:
            <input type="text" placeholder="Full Name" bind:value={name} required />
        </h4>
        <h4>
            Date of Birth:
            <input type="date" placeholder="Date of Birth" bind:value={dateOfBirth} required />
        </h4>
        <h4>
            Gender:
            <input type="text" placeholder="Gender" bind:value={gender} required />
        </h4>
        <h4>
            Contact Number:
            <input type="contact" placeholder="Contact Number" bind:value={contact} required />
        </h4>
        <h4>
            Address:
            <input type="address" placeholder="Address" bind:value={address} required />
        </h4>
        <h4>
            Email ID:
            <input type="email" placeholder="Email ID" bind:value={email} required />
        </h4>
        
        {#if error}
            <p class="error">{error}</p>
        {/if}
        <button type="submit">Register</button>
    </form>
</div>
{/if}

<style>
    .dashboard {
		max-width: 800px;
		margin: 2rem auto;
		padding: 2rem;
		border-radius: 10px;
		box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
		background-color: #f1f1f1;
		font-family: Arial, sans-serif;
	}
    form {
        max-width: 400px;
        margin: 2rem auto;
        padding: 2rem;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
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
    .card {
		border: 1px solid #ddd;
		border-radius: 10px;
		padding: 1rem;
		background: #f9f9f9;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
	}
    .home-button {
	display: inline-block;
	margin: 1.5rem 2rem;
	padding: 0.6rem 1.2rem;
	background-color: #007bff;
	color: white;
	text-decoration: none;
	border-radius: 6px;
	font-weight: 500;
	transition: background-color 0.3s ease;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.home-button:hover {
	background-color: #0056b3;
}
.header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1rem 2rem;
	background-color: #f1f1f1;
	border-bottom: 1px solid #ddd;
}

.logout-button {
	background-color: #dc3545;
	color: white;
	border: none;
	margin: 1.5rem 2rem;
	border-radius: 6px;
	padding: 0.6rem 1.2rem;
	cursor: pointer;
	font-weight: 500;
	transition: background-color 0.3s ease;
}

.logout-button:hover {
	background-color: #c82333;
}

</style>
