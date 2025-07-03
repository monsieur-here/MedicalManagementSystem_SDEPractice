<script>
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";
  import { PATIENT_ROUTES } from "$lib/routes";
  import { onMount } from "svelte";
  import { createAppointment } from "../../../apis/patient/appointment";

  let doctors = [
    {
      id: 1,
      name: "Dr. Smith",
      specialty: "Cardiologist",
      schedule: ["2025-06-10 10:00", "2025-06-20 14:00", "2025-06-21 09:00"],
    },
    {
      id: 2,
      name: "Dr. Jane Doe",
      specialty: "Dermatologist",
      schedule: ["2025-06-10 11:00", "2025-06-12 15:00"],
    },
    {
      id: 3,
      name: "Dr. Dane Marsch",
      specialty: "Cardiologist",
      schedule: ["2025-06-10 11:00", "2025-06-12 15:00"],
    },
    {
      id: 4,
      name: "Dr. Peter Smith",
      specialty: "Dermatologist",
      schedule: ["2025-06-10 11:00", "2025-06-12 15:00"],
    },
    {
      id: 5,
      name: "Dr. Andrea Backer",
      specialty: "General Physician",
      schedule: ["2025-06-10 11:00", "2025-06-12 15:00"],
    },
    {
      id: 6,
      name: "Dr. Daniel Christian",
      specialty: "Neurologist",
      schedule: ["2025-06-10 11:00", "2025-06-12 15:00"],
    },
  ];

  let selectedDoctor = "";
  let selectedTime = "";
  let bookingMessage = "";

  let name = "";
  let date = "";
  let time = "";
  let reason = "";
  let specialist = "";

  let loginUser = null;

  onMount(() => {
    loginUser = JSON.parse(window.localStorage.getItem("user_data"));
  });

  async function handleBooking(e) {
    const data = new FormData(e.currentTarget);

    if (!data.get("specialist")) {
      bookingMessage = "Please select a doctor!";
      return;
    }

    const payload = {
      user_id: loginUser?.id,
      doctor_id: Number(data.get("specialist")),
      notes: data.get("reason"),
    };

    const response = await createAppointment(payload);

    console.log(response);
  }
</script>

<div class="header">
  <a href={PATIENT_ROUTES.dashboard.url} class="home-button">Back</a>
  <h1>📅 Book Appointment</h1>
  <button class="logout-button" on:click={() => goto("/")}>Logout</button>
</div>

<!-- Form Fields Only -->
<form on:submit|preventDefault={handleBooking}>
  <div class="form-section">
    <div style="padding-left: 12px;">
      <p>
        {`- ${loginUser?.firstName} ${loginUser?.lastName}`}
      </p>
      <p>- {loginUser?.email}</p>
    </div>

    <input
      placeholder="Reason for Appointment"
      name="reason"
      bind:value={reason}
      required
    />

    <select bind:value={specialist} name="specialist" required>
      <option value="" disabled selected>Select Specialist</option>
      {#each doctors as doc (doc.id)}
        <option value={doc.id}>{`${doc.name} (${doc.specialty})`}</option>
      {/each}
    </select>
  </div>

  <!-- Confirmation Button at End -->
  <button type="submit" class="confirm-button">Confirm Appointment</button>
</form>

<!-- Optional feedback message -->
{#if bookingMessage}
  <p class="message">{bookingMessage}</p>
{/if}

<style>
  h1 {
    text-align: center;
    margin-top: 2rem;
    color: #007bff;
  }

  form {
    display: flex;
    flex-direction: column;
    max-width: 900px;
    margin: 2rem auto;
    gap: 2rem;
  }

  .form-section {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 1rem;
    background: #f5f5f5;
    border-radius: 8px;
  }

  input,
  select {
    padding: 0.6rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

  .confirm-button {
    align-self: center;
    padding: 0.8rem 2rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.2s;
  }

  .confirm-button:hover {
    background-color: #0056b3;
  }

  .message {
    text-align: center;
    color: green;
    font-weight: bold;
    margin-top: 1rem;
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
