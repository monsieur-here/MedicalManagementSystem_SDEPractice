<script>
  import { RECEPTIONIST_ROUTES } from "$lib/routes";
  import { handleLogOut } from "$lib/utils";
  import { onMount } from "svelte";
  import {
    getReceptionistAppointment,
    putReceptionistAppointment,
  } from "../../../apis/receptionist/appointment";

  let appointmentData = [];
  let selectedDateTime = "";

  onMount(async () => {
    appointmentData = await getReceptionistAppointment();
  });

  function handleChange(event, app) {
    selectedDateTime = event.target.value;

    const [date, time] = selectedDateTime.split("T");
    const formattedDateTime = `${date} ${time}:00`;

    // put api call ...
    const payload = {
      appointment_id: app?.appointment?.id,
      appointment_date: formattedDateTime,
      status: "SCHEDULED",
    };

    handlePutApp(payload);
  }

  const handlePutApp = async (payload) => {
    const updateApp = await putReceptionistAppointment(payload);

    if (updateApp?.code === 200) {
      alert("Appointment has been updated!");
      appointmentData = await getReceptionistAppointment();
    }
  };
</script>

<div>
  <div class="header">
    <div></div>
    <h1 class="title">🩺 Receptionist Dashboard</h1>
    <button class="logout-button" on:click={handleLogOut}>Logout</button>
  </div>

  <div class="dashboard">
    <div class="prescription-container">
      {#each appointmentData as appointment}
        <div class="prescription-card">
          <div class="prescription-title">
            Patient: {appointment?.patient?.first_name +
              " " +
              appointment?.patient?.last_name}
          </div>

          {#if appointment?.doctor?.first_name}
            <div>
              <span class="label">Doctor:</span>
              <span class="value"
                >{appointment?.doctor?.first_name ??
                  "" + " " + appointment?.doctor?.last_name ??
                  ""}</span
              >
            </div>
          {/if}

          {#if appointment?.appointment?.appointmentDate}
            <div>
              <span class="label">Appointment Date:</span>
              <span class="value"
                >{appointment?.appointment?.appointmentDate}</span
              >
            </div>
          {/if}

          {#if appointment?.appointment?.notes}
            <div>
              <span class="label">Appointment Notes:</span>
              <span class="value">{appointment?.appointment?.notes}</span>
            </div>
          {/if}

          <div>
            <span class="label">Appointment status:</span>
            <span class="value">{appointment?.appointment?.status}</span>
          </div>

          {#if !appointment?.appointment?.appointmentDate}
            <div>
              <label>
                Select Date & Time:
                <input
                  type="datetime-local"
                  on:change={(e) => handleChange(e, appointment)}
                />
              </label>
            </div>
          {:else}
            <div>
              <span class="label">Appointment status:</span>
              <span class="value"
                >{new Date(
                  appointment?.appointment?.appointmentDate
                ).toLocaleString()}</span
              >
            </div>
          {/if}
        </div>
      {/each}
    </div>
  </div>
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
