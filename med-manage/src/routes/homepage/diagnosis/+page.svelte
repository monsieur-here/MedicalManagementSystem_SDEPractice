<script>
  import { goto } from "$app/navigation";
  import { onMount } from "svelte";
  import { page } from "$app/stores";

	let appointmentId = "";
	let diagnosis = "";
	let successMessage = "";
	let criticality="";
	/**
     * @type {any[]}
     */
	let medications = [];

	let newMed = {
		name: "",
		dosage:"",
		frequency:"",
		dateIssued: "",
		description: ""
	};

  let newMed = {
    name: "",
    dateIssued: "",
    description: "",
  };

  const diagnosesList = [
    "Flu",
    "Diabetes",
    "Hypertension",
    "Asthma",
    "COVID-19",
    "Other",
  ];

  function handleSubmit() {
    if (!newMed.name || !newMed.dateIssued || !newMed.description) {
      successMessage = "⚠️ Please fill out all medication fields.";
      return;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0); // Remove time part

    const issuedDate = new Date(newMed.dateIssued);
    issuedDate.setHours(0, 0, 0, 0); // Remove time part
	if (issuedDate < today) {
		successMessage = "⚠️ Medication issue date cannot be in the past.";
		return;
	}
	// Validate appointment ID is numeric
	if (!/^\d+$/.test(appointmentId)) {
		successMessage = "⚠️ Appointment ID must contain numbers only.";
		return;
	}
	
	medications.push({ ...newMed });
	newMed = { name: "", dosage:"", frequency:"", dateIssued: "", description: "" };
	successMessage = ""; // Clear any previous error
		if (!appointmentId || !diagnosis || medications.length === 0) {
			successMessage = "⚠️ Please fill out all fields and add at least one medication.";
			return;
		}

    if (issuedDate < today) {
      successMessage = "⚠️ Medication issue date cannot be in the past.";
      return;
    }
    // Validate appointment ID is numeric
    if (!/^\d+$/.test(appointmentId)) {
      successMessage = "⚠️ Appointment ID must contain numbers only.";
      return;
    }

    medications.push({ ...newMed });
    newMed = { name: "", dateIssued: "", description: "" };
    successMessage = ""; // Clear any previous error
    if (!appointmentId || !diagnosis || medications.length === 0) {
      successMessage =
        "⚠️ Please fill out all fields and add at least one medication.";
      return;
    }

    // Here you'd usually send the data to your backend
    successMessage = "✅ Diagnosis and medications submitted successfully!";
    // Optionally reset
    appointmentId = "";
    diagnosis = "";
    medications = [];
  }
</script>

<div class="header">
  <a href={`/homepage?user_type=Doctor`} class="home-button">Back</a>
  <h1 class="title">🩺 Patient Diagnosis</h1>
  <a href="/" class="logout-button">Logout</a>
</div>

<div class="form-container">
  <input
    type="text"
    inputmode="numeric"
    pattern="[0-9]*"
    placeholder="Appointment ID"
    bind:value={appointmentId}
    required
  />
  <select bind:value={diagnosis} required>
    <option value="" disabled selected>Select Diagnosis</option>
    {#each diagnosesList as diag}
      <option>{diag}</option>
    {/each}
  </select>

  <h3>Prescribe Medication</h3>
  <div class="medication-form">
    <input
      type="text"
      placeholder="Medication Name(s)"
      bind:value={newMed.name}
    />
    <input
      type="date"
      bind:value={newMed.dateIssued}
      min={new Date().toISOString().split("T")[0]}
    />
    <textarea placeholder="Description" bind:value={newMed.description}
    ></textarea>
  </div>

  {#if medications.length}
    <h4>Prescribed Medications</h4>
    <ul>
      {#each medications as med, index}
        <li>
          <strong>{med.name}</strong> — {med.dateIssued}<br />
          <em>{med.description}</em>
        </li>
      {/each}
    </ul>
  {/if}

  <button class="submit-button" on:click={handleSubmit}>Submit Diagnosis</button
  >

  {#if successMessage}
    <div class="popup">{successMessage}</div>
  {/if}
</div>

<style>
  h1 {
    text-align: center;
    color: #007bff;
    margin-top: 2rem;
  }

  .form-container {
    max-width: 600px;
    margin: auto;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 2rem;
    background: #f7f7f7;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  input,
  select,
  textarea {
    padding: 0.7rem;
    border-radius: 5px;
    border: 1px solid #ccc;
    width: 100%;
  }

  textarea {
    resize: vertical;
    height: 80px;
  }

  .medication-form {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }

  button {
    padding: 0.7rem;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }

  .submit-button {
    background-color: #28a745;
  }

  .popup {
    text-align: center;
    margin-top: 1rem;
    padding: 1rem;
    background-color: #e0ffe0;
    color: green;
    border-radius: 5px;
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
