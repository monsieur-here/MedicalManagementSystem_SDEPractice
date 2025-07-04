<script>
  import { DOCTORS_ROUTES } from "$lib/routes";
  import { onMount } from "svelte";
  import { getDoctorsAppointment } from "../../../apis/doctor/appointment";
  import { BASE_API_URL } from "$lib/config";
  import DoctorAppointment from "$lib/components/DoctorAppointment.svelte";
  import { handleLogOut } from "$lib/utils";

  let page = 1;
  let pageSize = 20;

  let appointmentData = [];

  onMount(() => {
    hydrateRecords();
  });

  const hydrateRecords = async () => {
    appointmentData = await getDoctorsAppointment();
  };
</script>

<div class="header">
  <div></div>
  <h1 class="title">🩺 Doctor Dashboard</h1>
  <button class="logout-button" on:click={handleLogOut}>Logout</button>
</div>

<div class="dashboard">
  <div class="prescription-container">
    {#each appointmentData as appointment}
      <div class="prescription-card">
        <div class="prescription-title">{appointment.prescriptionName}</div>

        <div>
          <span class="label">Patient:</span>
          <span class="value"
            >{`${appointment?.patient?.first_name} ${appointment?.patient?.last_name}`}</span
          >
        </div>

        {#if appointment?.doctor?.first_name}
          <div>
            <span class="label">Doctor:</span>
            <span class="value"
              >{`${appointment?.doctor?.first_name} ${appointment?.doctor?.last_name}`}</span
            >
          </div>
        {/if}

        <div>
          <span class="label">Doctor's Specialisation:</span>
          <span class="value">{appointment?.doctor?.specialization}</span>
        </div>

        {#if appointment?.appointment?.appointmentDate}
          <div>
            <span class="label">Appointment Date:</span>
            <span class="value"
              >{appointment?.appointment?.appointmentDate}</span
            >
          </div>
        {/if}

        <div>
          <span class="label">Appointment note:</span>
          <span class="value">{appointment?.appointment?.notes}</span>
        </div>

        <div>
          <span class="label">Appointment status:</span>
          <span class="value">{appointment?.appointment?.status}</span>
        </div>

        <div>
          <DoctorAppointment {appointment} {hydrateRecords} />
        </div>
      </div>
    {/each}
  </div>
</div>

<style>
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

  .prescription-container {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    padding: 1rem;
  }

  .prescription-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 1rem;
    width: 100%;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
    transition: transform 0.2s ease;
    cursor: pointer;
  }

  .prescription-card:hover {
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  }

  .prescription-card div {
    margin-bottom: 0.5rem;
    font-size: 0.95rem;
  }

  .prescription-title {
    font-weight: 600;
    font-size: 1.1rem;
    margin-bottom: 0.75rem;
    color: #333;
  }

  .label {
    font-weight: 500;
    color: #555;
  }

  .value {
    font-weight: 400;
    color: #222;
  }
</style>
