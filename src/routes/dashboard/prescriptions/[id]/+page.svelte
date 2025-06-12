<script>
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	// Get ID from URL
	$: id = Number($page.params.id);

	// Fake data lookup
	let prescriptions = [
		{id:1,  name: 'Amoxicillin', dosage: '500mg', frequency: '3 times a day', date: '2025-05-20' },
		{id:2,  name: 'Paracetamol', dosage: '650mg', frequency: '2 times a day', date: '2025-05-22' },
		{id:3,  name: 'Ibuprofen', dosage: '400mg', frequency: 'as needed', date: '2025-05-24' }
    ];
    
	// Find prescription by id property, not by array index
    //let prescription = prescriptions[2];
    $: prescription = prescriptions.find(p => p.id === id);
    console.log(prescription);

</script>

<div class="header">
	<a href="/dashboard/prescriptions" class="home-button">Back</a>
	<h1 class="title">Prescriptions</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
{#if prescription}
	<h2 style="text-align: center;">Prescription Details</h2>

	<div class="card">
		<p><strong>Name:</strong> {prescription.name}</p>
		<p><strong>Dosage:</strong> {prescription.dosage}</p>
		<p><strong>Frequency:</strong> {prescription.frequency}</p>
		<p><strong>Date Issued:</strong> {prescription.date}</p>
	</div>

{:else}
	<p>Prescription not found.</p>
{/if}

<style>
	.card {
		max-width: 400px;
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
