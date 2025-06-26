<script>
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";

  $: user_type = $page.url.searchParams.get("user_type");
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
  ];

  let selectedDoctor = "";
  let selectedTime = "";
  let bookingMessage = "";

  let name = "";
  let date = "";
  let time = "";
  let reason = "";
  let specialist = "";

  function handleBooking() {
    if (!selectedDoctor || !selectedTime) {
      bookingMessage = "Please select a doctor and a time slot.";
      return;
    }

    const selectedDateTime = new Date(selectedTime);
    const now = new Date();

    if (selectedDateTime < now) {
      bookingMessage = "❌ You cannot book an appointment in the past.";
      return;
    }

    const details = {
      name,
      date: selectedTime.split(" ")[0], // extracts date
      time: selectedTime.split(" ")[1], // extracts time
      reason,
      specialist,
    };

    const query = new URLSearchParams(details).toString();
    goto(`/dashboard/appointments/confirm?${query}`);
  }

  function bookAppointment() {
    if (!selectedDoctor || !selectedTime) {
      bookingMessage = "Please select a future time slot.";
      return;
    }

    bookingMessage = `✅ Appointment booked with ${selectedDoctor} at ${selectedTime}`;
    selectedDoctor = "";
    selectedTime = "";
  }
  $: filteredDoctors = doctors.map((doctor) => {
    const slotsForDate = doctor.schedule.filter((slot) => {
      const [slotDate] = slot.split(" ");
      const today = new Date().toISOString().split("T")[0];

      // Only match slots for the selected date
      if (!date || slotDate !== date) return false;

      // Ensure time is not in the past if it's today
      const slotDateTime = new Date(slot);
      const now = new Date();

      return slotDateTime >= now;
    });

    return { ...doctor, filteredSchedule: slotsForDate };
  });
</script>

<div class="header">
  <a href={`/dashboard?user_type=${user_type}`} class="home-button">Back</a>
  <h1>📅 Book Appointment</h1>
  <button class="logout-button" on:click={() => goto("/")}>Logout</button>
</div>

<!-- Form Fields Only -->
<form on:submit|preventDefault={handleBooking}>
  <div class="form-section">
    <input placeholder="Patient Name" bind:value={name} required />
    <input type="date" bind:value={date} required />
    <input placeholder="Reason for Appointment" bind:value={reason} required />
    <select bind:value={specialist} required>
      <option value="" disabled selected>Select Specialist</option>
      <option>Cardiologist</option>
      <option>Dermatologist</option>
      <option>General Physician</option>
      <option>Neurologist</option>
    </select>
  </div>

  <!-- Doctor Cards -->
  <div class="doctors">
    {#each filteredDoctors as doctor (doctor.id)}
      {#if doctor.filteredSchedule.length > 0}
        <div class="card">
          <h2>{doctor.name}</h2>
          <p><strong>Specialty:</strong> {doctor.specialty}</p>
          <p><strong>Available Slots:</strong></p>
          <ul>
            {#each doctor.filteredSchedule as slot}
              <li>
                <input
                  type="radio"
                  name="appointment"
                  id="{doctor.id}-{slot}"
                  value={slot}
                  bind:group={selectedTime}
                  on:change={() => (selectedDoctor = doctor.name)}
                />
                <label for="{doctor.id}-{slot}">{slot}</label>
              </li>
            {/each}
          </ul>
        </div>
      {/if}
    {/each}
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

  .doctors {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 1.5rem;
  }

  .card {
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 1rem;
    background: #f9f9f9;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
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
