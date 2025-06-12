<script>
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	// Dummy detailed reports
	const reports = [
		{
			id: 1,
			title: 'Blood Test',
			date: '2025-05-15',
			content: 'Complete blood count is within normal range. No signs of infection or anemia.'
		},
		{
			id: 2,
			title: 'X-Ray Chest',
			date: '2025-05-20',
			content: 'Mild inflammation detected in the lower left lung. Follow-up recommended.'
		},
		{
			id: 3,
			title: 'MRI Brain',
			date: '2025-05-25',
			content: 'No abnormalities found. Brain structure appears normal.'
		}
	];

	$: id = Number($page.params.id);
	$: report = reports.find(r => r.id === id);

</script>
<div class="header">
	<a href="/dashboard/reports" class="home-button">Back</a>
	<h1 class="title">Prescriptions</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
{#if report}
	<h2 style="text-align: center;">{report.title}</h2>
	<div class="card">
		<p><strong>Date:</strong> {report.date}</p>
		<p><strong>Details:</strong></p>
		<p>{report.content}</p>
	</div>
{:else}
	<p>Report not found.</p>
{/if}

<style>
	.card {
		max-width: 500px;
		margin: 2rem auto;
		padding: 1.5rem;
		background: #f0f8ff;
		border-radius: 8px;
		box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	}
	button {
		display: block;
		margin: 2rem auto 0;
		padding: 0.5rem 1rem;
		border: none;
		background-color: #007bff;
		color: white;
		border-radius: 4px;
		cursor: pointer;
	}
    button:hover {
		background-color: #2b8df7;
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
