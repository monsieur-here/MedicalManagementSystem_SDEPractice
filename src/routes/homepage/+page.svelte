<script>
	import { goto } from "$app/navigation";
    import { page } from "$app/stores";
    $: user_type = $page.url.searchParams.get("user_type");    
	//let staff_type="";
	//let appointment_id=0;
	// function navigateTo('') {
	// 	goto('');
	// }
	let staff_type = "Receptionist"; // Default value; could be loaded from login info
	let appointment_id = "";
    localStorage.setItem('staff_type', staff_type);

</script>
<div class="header">
	<a href={`/homepage?user_type=${user_type}`} class="home-button">Home</a>
	<h1 class="title">🏥 Welcome to MedCare Portal</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
<div class="tabs">
	<button
		class:selected={staff_type === 'Receptionist'}
		on:click={() => staff_type = 'Receptionist'}>
		Receptionist
	</button>
	<button
		class:selected={staff_type === 'Doctor'}
		on:click={() => staff_type = 'Doctor'}>
		Doctor
	</button>
</div>
{#if staff_type === "Receptionist"}
	<div class="grid">
		<div class="card" on:click={() => goto(`/registration?user_type=${user_type}`)} tabindex="0">
			<h2>Patient Registration</h2>
			<p>Register a new patient in the hospital system.</p>
		</div>

		<div class="card" on:click={() => goto('/homepage/appointments?staff_type=Receptionist')} tabindex="0">
			<h2>Appointment List</h2>
			<p>View and manage all patient appointments.</p>
		</div>

		<div class="card" on:click={() => goto('/homepage/doctors?user_type=Staff')} tabindex="0">
			<h2>Doctor Availability</h2>
			<p>Check which doctors are available and when.</p>
		</div>

		<div class="card" on:click={() => goto('/homepage/room?user_type=Staff')} tabindex="0">
			<h2>Book a Room</h2>
			<p>Manage room booking for the patient.</p>
		</div>
	</div>
{:else if staff_type === "Doctor"}
	<div class="grid">
		<div class="card" on:click={() => goto(`/homepage/diagnosis`)} tabindex="0">
			<h2>Patient Diagnosis</h2>
			<p>Diagnosis of a patient's health and prescription of medicines.</p>
		</div>

		<div class="card" on:click={() => goto('/homepage/appointments?staff_type=Doctor')} tabindex="0">
			<h2>Appointment List</h2>
			<p>View and manage all patient appointments.</p>
		</div>
	</div>
{:else}
	<p class="empty">Invalid Access!</p>
{/if}

<style>
	.title {
		text-align: center;
		margin-top: 2rem;
		color: #007bff;
	}

	.grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
		gap: 1.5rem;
		max-width: 1000px;
		margin: 2rem auto;
		padding: 1rem;
	}

	.card {
		background: #f0f8ff;
		border-radius: 10px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		padding: 1.5rem;
		cursor: pointer;
		transition: transform 0.2s;
		outline: none;
	}

	.card:hover, .card:focus {
		transform: translateY(-5px);
		box-shadow: 0 6px 15px rgba(0, 123, 255, 0.2);
	}

	.card h2 {
		margin: 0 0 0.5rem;
		color: #333;
	}

	.card p {
		color: #555;
	}
	.header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0rem 1rem;
	background-color: #f1f1f1;
	border-bottom: 1px solid #ddd;
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
.tabs {
		display: flex;
		justify-content: center;
		gap: 1rem;
		margin-top: 1.5rem;
	}
	.tabs button {
		padding: 0.7rem 1.5rem;
		border: 2px solid #007bff;
		border-radius: 8px;
		background-color: white;
		color: #007bff;
		font-weight: bold;
		cursor: pointer;
		transition: all 0.2s ease;
	}
	.tabs button.selected {
		background-color: #007bff;
		color: white;
	}
	.tabs button:hover {
		background-color: #0056b3;
		color: white;
	}
</style>
