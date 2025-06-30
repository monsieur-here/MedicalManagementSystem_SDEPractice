<script>
  //to check if doctors are available in receptionist portal
  import { onMount } from 'svelte';
  import { goto } from '$app/navigation';

  let selectedDate = new Date().toISOString().split("T")[0];
  let selectedConsultationType = '';
  let selectedDoctor = '';
  let selectedTime = '';
  let showConfirmation = false;
  let patientId=0;
  let visitDescription='';
  let status='';

  const consultationTypes = ['General', 'Dental', 'Cardiology', 'Dermatology'];

  const doctors = [
    { doctorId: 1, doctorName: 'Dr. Smith', specialist: 'General' },
    { doctorId: 2, doctorName: 'Dr. Adams', specialist: 'Dental' },
    { doctorId: 3, doctorName: 'Dr. Gomez', specialist: 'Cardiology' },
    { doctorId: 4, doctorName: 'Dr. Patel', specialist: 'Dermatology' }
  ];

  const slots = ['09:00 AM', '10:00 AM', '11:00 AM', '02:00 PM', '03:00 PM', '04:00 PM'];

  let availability = Object.create(null);

  onMount(() => {
    doctors.forEach(doc => {
      availability[doc.doctorId] = {};
      for (let i = 0; i < 7; i++) {
        const date = new Date();
        date.setDate(date.getDate() + i);
        const dateStr = date.toISOString().split("T")[0];
        availability[doc.doctorId][dateStr] = slots.filter(() => Math.random() > 0.3);
      }
    });
  });

  $: filteredDoctors = selectedConsultationType
    ? doctors.filter(doc => doc.specialist === selectedConsultationType)
    : doctors;

  $: availableSlots = selectedDoctor && selectedDate
    ? availability[+selectedDoctor]?.[selectedDate] || []
    : [];

  /**
     * @param {string} time
     */
  function confirmAppointment(time) {
  const now = new Date();
  const slotDateTime = new Date(`${selectedDate}T${convertTo24Hour(time)}`);

  // Add 5 hours to the current time
  const minBookingTime = new Date(now.getTime() + 5 * 60 * 60 * 1000);

  if (slotDateTime < minBookingTime) {
    alert("❌ You can only book appointments at least 5 hours in advance.");
    return;
  }

  selectedTime = time;
  showConfirmation = true;
  status="Scheduled";

  setTimeout(() => {
    showConfirmation = false;
    selectedTime = '';
  }, 3000);
}

    /**
     * @param {string} timeStr
     */
function convertTo24Hour(timeStr) {
  const [time, meridian] = timeStr.split(" ");
  let [hours, minutes] = time.split(":").map(Number);

  if (meridian === "PM" && hours !== 12) hours += 12;
  if (meridian === "AM" && hours === 12) hours = 0;

  // Ensure two-digit formatting
  const pad = (/** @type {number} */ n) => String(n).padStart(2, '0');
  return `${pad(hours)}:${pad(minutes)}:00`;
}

</script>

<div class="header">
	<a href={`/receptionists`} class="home-button">Back</a>
	<h1 class="title">📋 Appointment List</h1>
	<a href="/" class="logout-button">Logout</a>
</div>
<div class="container">
  <div class="filters">
    <label>
      Consultation Type:
      <select bind:value={selectedConsultationType}>
        <option value="">-- Select Type --</option>
        {#each consultationTypes as type}
          <option>{type}</option>
        {/each}
      </select>
    </label>

    <label>
      Doctor:
      <select bind:value={selectedDoctor}>
        <option value="">-- Select Doctor --</option>
        {#each filteredDoctors as doc}
          <option value={doc.doctorId}>{doc.doctorName}</option>
        {/each}
      </select>
    </label>

    <label>
      Date:
      <input type="date" bind:value={selectedDate} min={new Date().toISOString().split('T')[0]} />
    </label>
  </div>
  

  {#if selectedDoctor && selectedDate}
    <div class="slots">
	<h2>Available Slots for {doctors.find(d => d.doctorId === +selectedDoctor)?.doctorName} on {selectedDate}</h2>
      {#if availableSlots.length > 0}
        <ul>
          {#each availableSlots as time}
			<li><button on:click={() => confirmAppointment(time)}>{time}</button></li>
          {/each}
        </ul>
      {:else}
        <p>No slots available.</p>
      {/if}
    </div>
  {/if}

  {#if showConfirmation}
  <div class="popup">
    ✅ Appointment confirmed with {doctors.find(d => d.doctorId === +selectedDoctor)?.doctorName} on {selectedDate} at {selectedTime}
  </div>
{/if}

</div>

<style>
  .container {
    max-width: 700px;
    margin: auto;
    padding: 2rem;
    font-family: sans-serif;
  }
  h1 {
    color: #007bff;
    text-align: center;
  }
  .filters {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin: 2rem 0;
  }
  label {
    display: flex;
    flex-direction: column;
    font-weight: bold;
  }
  select, input[type="date"] {
    padding: 0.5rem;
    font-size: 1rem;
    border-radius: 6px;
    border: 1px solid #ccc;
  }
  .slots ul {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    padding: 0;
  }
  .slots li {
    list-style: none;
  }
  button {
    padding: 0.6rem 1.2rem;
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
	.popup {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
  color: #155724;
  border-radius: 6px;
  font-weight: bold;
  text-align: center;
}
</style>
