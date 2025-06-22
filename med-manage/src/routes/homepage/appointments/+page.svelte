<script>
	import { goto } from "$app/navigation";
	import { page } from "$app/stores";

	$: user_type = $page.url.searchParams.get("user_type");

	let appointments = [
		{
			id: 1,
			patientName: "John Doe",
			doctor: "Dr. Smith",
			date: "2025-06-20",
			time: "10:00 AM",
			status: "Scheduled",
		},
		{
			id: 2,
			patientName: "Jane Doe",
			doctor: "Dr. Adams",
			date: "2025-07-15",
			time: "2:30 PM",
			status: "Scheduled",
		},
		{
			id: 3,
			patientName: "Ellen Lee",
			doctor: "Dr. Gomez",
			date: "2025-05-05",
			time: "4:00 PM",
			status: "Scheduled",
		},
	];

	let selectedDate = new Date().toISOString().split("T")[0]; // default: today (YYYY-MM-DD)

	// Filtered appointments based on date within ±30 days
	$: filteredAppointments = appointments.filter(app => {
		const appointmentDate = new Date(app.date);
		const refDate = new Date(selectedDate);
		const lowerBound = new Date(refDate);
		const upperBound = new Date(refDate);
		lowerBound.setDate(refDate.getDate() - 30);
		upperBound.setDate(refDate.getDate() + 30);
		return appointmentDate >= lowerBound && appointmentDate <= upperBound;
	});
	/** @param {number} id @param {string} newStatus */
	function updateStatus(id, newStatus) {
		appointments = appointments.map((app) =>
			app.id === id ? { ...app, status: newStatus } : app,
		);
	}

	/** @param {number} id */
	function cancelAppointment(id) {
		if (confirm("Are you sure you want to cancel this appointment?")) {
			appointments = appointments.filter((app) => app.id !== id);
		}
	}
</script>

<div class="header">
	<a href={`/homepage?user_type=${user_type}`} class="home-button">Back</a>
	<h1 class="title">📋 Appointment List</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
<div class="date-filter">
	<label for="date">📅 Filter appointments: </label>
	<input id="date" type="date" bind:value={selectedDate} />
</div>{#if filteredAppointments.length > 0}
	<div class="appointments">
		{#each filteredAppointments as app (app.id)}
			<div class="card">
				<h3>{app.patientName}</h3>
				<p><strong>Doctor:</strong> {app.doctor}</p>
				<p><strong>Date:</strong> {app.date}</p>
				<p><strong>Time:</strong> {app.time}</p>
				<p><strong>Status:</strong> {app.status}</p>
				<div class="actions">
					<button on:click={() => updateStatus(app.id, "Completed")}
						>Mark as Completed</button
					>
					<button
						on:click={() => cancelAppointment(app.id)}
						class="cancel">Cancel</button
					>
				</div>
			</div>
		{/each}
	</div>
{:else}
	<p class="empty">No appointments scheduled.</p>
{/if}

<style>
	.title {
		text-align: center;
		margin-top: 2rem;
		color: #007bff;
	}

	.appointments {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
		gap: 1.5rem;
		padding: 2rem;
	}

	.card h3 {
		margin: 0 0 0.5rem;
	}

	.card p {
		margin: 0.3rem 0;
	}

	.actions {
		margin-top: 1rem;
		display: flex;
		gap: 1rem;
	}

	button {
		padding: 0.5rem 1rem;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		background-color: #007bff;
		color: white;
		transition: background-color 0.2s;
	}

	button:hover {
		background-color: #0056b3;
	}

	button.cancel {
		background-color: #dc3545;
	}

	button.cancel:hover {
		background-color: #b02a37;
	}

	.empty {
		text-align: center;
		color: #666;
		margin-top: 3rem;
		font-style: italic;
	}
	.card {
		background-color: #f9f9f9;
		padding: 1.5rem;
		border-radius: 8px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	}
	.date-filter {
	text-align: center;
	margin-top: 1.5rem;
}

.date-filter label {
	margin-right: 0.5rem;
	font-weight: 500;
	color: #333;
}

.date-filter input[type="date"] {
	padding: 0.4rem 0.8rem;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 1rem;
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
