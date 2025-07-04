<script>
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";
  import { AUTH_ROUTES, PATIENT_ROUTES } from "$lib/routes";
  import { onMount } from "svelte";
  import { createAppointment } from "../../../apis/patient/appointment";
  import { handleLogOut } from "$lib/utils";
  import { getDoctorsList } from "../../../apis/doctor/appointment";

  let doctors = [];

  let selectedDoctor = "";
  let selectedTime = "";
  let bookingMessage = "";

  let name = "";
  let date = "";
  let time = "";
  let reason = "";
  let specialist = "";

  let loginUser = null;

  onMount(async () => {
    loginUser = JSON.parse(window.localStorage.getItem("user_data"));
    doctors = await getDoctorsList();
  });

  async function handleBooking(e) {
    const data = new FormData(e.currentTarget);

    if (!data.get("specialist")) {
      bookingMessage = "Please select a doctor!";
      return;
    }

    const payload = {
      patient_id: loginUser?.id,
      doctor_id: Number(data.get("specialist")),
      notes: data.get("reason"),
    };

    const response = await createAppointment(payload);

    if (response.code === 200) {
      alert("Appointment Request has been sent successfully!");
      goto(PATIENT_ROUTES.dashboard.url);
    } else {
      alert("Oops, something went wrong while creating appointment!");
    }
  }
</script>

<div class="header">
  <a href={PATIENT_ROUTES.dashboard.url} class="home-button">Back</a>
  <h1>📅 Book Appointment</h1>
  <button class="logout-button" on:click={handleLogOut}>Logout</button>
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
        <option value={doc.id}
          >{doc.firstName +
            " " +
            doc.lastName +
            " | " +
            doc.specialization}</option
        >
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
