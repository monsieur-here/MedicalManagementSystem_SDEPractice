<script>
    import { goto } from "$app/navigation";
    import { page } from "$app/stores";
    $: user_type = $page.url.searchParams.get("user_type");    
	const user = {
		name: "John Doe",
		patientId: "P123456",
	};
</script>
<div class="header">
	<a href="/dashboard?user_type=${user_type}" class="home-button">Home</a>
	<h1 class="title">🏥 Welcome to MedCare Portal</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
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

	h1 {
		text-align: center;
	}

	.card-container {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		gap: 1.5rem;
		margin-top: 2rem;
	}

	.card {
		background: #f9f9f9;
		border-radius: 10px;
		padding: 1.5rem;
		width: 220px;
		text-align: center;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
		cursor: pointer;
		transition: all 0.2s ease-in-out;
	}

	.card:hover {
		transform: scale(1.05);
		background: #e6f0ff;
	}

	a {
		text-decoration: none;
		color: #007bff;
		font-weight: bold;
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
</style>

<div class="dashboard" >
	<p style="text-align: center;">Patient ID: {user.patientId}</p>

	<div class="card-container">
		<div class="card">
			<a href="/dashboard/appointments?user_type=Patient">📅 Book Appointment</a>
		</div>

		<div class="card">
			<a href="/dashboard/prescriptions?user_type=Patient">📄 View Prescriptions</a>
		</div>

		<div class="card">
			<a href="/dashboard/reports?user_type=Patient">🧪 Medical Reports</a>
		</div>
	</div>
</div>
