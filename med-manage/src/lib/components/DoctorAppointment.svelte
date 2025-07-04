<script>
  export let appointment;
  export let hydrateRecords;

  import { addPrescription } from "../../apis/doctor/appointment";
  import { putReceptionistAppointment } from "../../apis/receptionist/appointment";

  let isOpen = false;

  let prescriptionName = "";
  let medication = "";
  let dosage = "";
  let frequency = "";
  let criticality = "";

  let loading = false;

  function toggleBox() {
    isOpen = !isOpen;
  }

  const handleAddPrescription = async () => {
    loading = true;

    const payload = {
      appointment_id: appointment?.appointment?.id,
      prescription_name: prescriptionName,
      medication: medication,
      dosage: dosage,
      frequency: frequency,
      criticality: criticality,
    };

    const res = await addPrescription(payload);

    if (res === "Prescription addded successfully.") {
      const date = new Date(appointment?.appointment?.appointmentDate);

      // Helper function to pad 0s
      const pad = (n) => n.toString().padStart(2, "0");

      // Build formatted string
      const formattedDate = `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;

      const updateApp = await putReceptionistAppointment({
        status: "COMPLETED",
        appointment_id: appointment?.appointment?.id,
        appointment_date: formattedDate,
      });

      if (updateApp?.code === 200) {
        alert(
          "Prescription added successfully for " +
            appointment?.patient?.first_name +
            " " +
            appointment?.patient?.last_name +
            "!"
        );

        hydrateRecords();

        // cache cleanup
        isOpen = false;
        prescriptionName = "";
        medication = "";
        dosage = "";
        frequency = "";
        criticality = "";
      }
    }

    loading = false;
  };
</script>

{#if appointment?.appointment?.appointmentDate && appointment?.appointment?.status !== "COMPLETED"}
  <button class="toggle-btn" on:click={toggleBox}>
    {isOpen ? "Hide" : "Show"} Details
  </button>
{/if}

{#if isOpen}
  <div class="box">
    <form
      on:submit|preventDefault={handleAddPrescription}
      class="card p-6 max-w-md mx-auto"
    >
      <label class="label">
        <span class="title">Prescription name</span>
        <input
          class="input"
          type="text"
          bind:value={prescriptionName}
          placeholder="Prescription"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span class="title">Medication</span>
        <input
          class="input"
          type="text"
          bind:value={medication}
          placeholder="Medication"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span class="title">Dosage</span>
        <input
          class="input"
          type="text"
          bind:value={dosage}
          placeholder="Dosage"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span class="title">Frequency</span>
        <input
          class="input"
          type="text"
          bind:value={frequency}
          placeholder="Frequency"
          disabled={loading}
        />
      </label>

      <label class="label">
        <span class="title">Criticality</span>
        <input
          class="input"
          type="text"
          bind:value={criticality}
          placeholder="Criticality"
          disabled={loading}
        />
      </label>

      <div style="display: flex; justify-content: end;">
        <button type="submit" class="login-button">Add Prescription</button>
      </div>
    </form>
  </div>
{/if}

{#if appointment?.appointment?.status === "REQUESTED"}
  <div style="background-color: antiquewhite; border-radius: 4px;">
    <p style="padding: 4px 8px;">Waiting for Appointment to be Scheduled</p>
  </div>
{/if}

<style>
  .box {
    border-radius: 8px;
    margin-top: 0.5rem;
    background: #f9f9f9;
  }
  label {
    display: flex;
  }
  form {
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
  }
  .title {
    min-width: 200px;
  }
  input {
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100%;
    padding: 0.7rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #fff;
  }
  .toggle-btn {
    background: #007acc;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 6px;
    cursor: pointer;
  }
  .login-button {
    padding: 0.5rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
