<script>
	let billId = 0;
	let patientId = 0;
	let patientName = '';
	let insuranceType = '';
	let billingDate = '';
	let email = '';
	let amount = '';
	let paymentMethod = '';
	let doctorId = '';
	let visitDescription = '';
	let showSuccess = false;

	const paymentOptions = ['Cash', 'Debit Card', 'Credit Card', 'Insurance'];

	function handlePayment() {
		if (patientName && email && amount && paymentMethod && billingDate && doctorId && visitDescription && insuranceType) {
			showSuccess = true;

			setTimeout(() => {
				billId = 0;
				patientId = 0;
				patientName = '';
				insuranceType = '';
				billingDate = '';
				email = '';
				amount = '';
				paymentMethod = '';
				doctorId = '';
				visitDescription = '';
				showSuccess = false;
			}, 3000);
		} else {
			alert("Please fill in all required fields.");
		}
	}
</script>

<div class="header">
	<a href={`/homepage?user_type=Staff`} class="home-button">Back</a>
	<h1 class="title">Checkout</h1>
	<a href="/" class="logout-button">Logout</a>
</div>

<div class="payment-container">
	<div class="form-section">
		<h2>📝 Visit & Billing Details</h2>
		<label>Patient ID:
			<input type="number" bind:value={patientId} min="1" />
		</label>
		<label>Patient Name:
			<input type="text" bind:value={patientName} />
		</label>
		<label>Email:
			<input type="email" bind:value={email} />
		</label>
	</div>

	<div class="form-section">
		<label>Billing Date:
			<input type="date" bind:value={billingDate} />
		</label>
		<label>Doctor ID:
			<input type="text" bind:value={doctorId} />
		</label>
		<label>Visit Description:
			<textarea bind:value={visitDescription} rows="3" placeholder="Short summary..."></textarea>
		</label>
		<label>Insurance Type:
			<input type="text" bind:value={insuranceType} />
		</label>
	</div>

	<div class="form-section">
		<h2>💰 Payment Details</h2>
		<label>Amount:
			<input type="number" bind:value={amount} min="1" />
		</label>
		<label>Payment Method:
			<select bind:value={paymentMethod}>
				<option value="" disabled selected>Select a payment method</option>
				{#each paymentOptions as method}
					<option value={method}>{method}</option>
				{/each}
			</select>
		</label>
	</div>

	<button on:click={handlePayment}>Make Payment</button>

	{#if showSuccess}
		<div class="popup">
			<p>✅ Payment for {patientName} completed successfully!</p>
		</div>
	{/if}
</div>

<style>
	.payment-container {
		max-width: 600px;
		margin: 2rem auto;
		padding: 2rem;
		background: #f8f9fa;
		border-radius: 10px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		font-family: sans-serif;
	}

	h1 {
		text-align: center;
		color: #007bff;
		margin-bottom: 1.5rem;
	}

	.form-section {
		margin-bottom: 2rem;
	}

	label {
		display: block;
		margin-bottom: 1rem;
		font-weight: 500;
	}

	input,
	select,
	textarea {
		width: 100%;
		padding: 0.5rem;
		margin-top: 0.25rem;
		border: 1px solid #ccc;
		border-radius: 4px;
		font-size: 1rem;
	}

	textarea {
		resize: vertical;
	}

	button {
		width: 100%;
		padding: 0.75rem;
		font-size: 1rem;
		background-color: #28a745;
		color: white;
		border: none;
		border-radius: 6px;
		cursor: pointer;
		transition: background-color 0.3s ease;
	}

	button:hover {
		background-color: #218838;
	}

	.popup {
		margin-top: 1.5rem;
		padding: 1rem;
		background-color: #d4edda;
		border: 1px solid #c3e6cb;
		color: #155724;
		border-radius: 4px;
		text-align: center;
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
</style>
