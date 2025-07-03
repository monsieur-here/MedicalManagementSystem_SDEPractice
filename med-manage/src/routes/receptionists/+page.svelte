<!--  -->
<script>
  import { onMount } from 'svelte';
  import { goto } from '$app/navigation';
  import { page } from '$app/stores';

  const daysToShow = 7;
  const today = new Date();
  const appointments = [
	{ date: today.toISOString().split('T')[0], patient: 'John Doe', doctor: 'Dr. Adams', slot: '09:00 AM' },
	{ date: "2025-07-01", patient: 'Jane Smith', doctor: 'Dr. Patel', slot: '10:30 AM' },
	{ date: "2025-06-30", patient: 'Alice Brown', doctor: 'Dr. Gomez', slot: '01:00 PM' },
  ];

  const patients = ['John Doe', 'Jane Smith', 'Alice Brown', 'Michael Johnson'];
  const doctors = ['Dr. Adams', 'Dr. Patel', 'Dr. Gomez', 'Dr. Lee'];
  const slots = ['09:00 AM', '10:30 AM', '01:00 PM', '02:30 PM', '04:00 PM'];

	$: weekDates = Array.from({ length: daysToShow }, (_, i) => {
  const d = new Date(today);
  d.setDate(d.getDate() + i);
  return d.toISOString().split("T")[0];
});

  // Generate random appointments for the next 7 days
  onMount(() => {
    for (let i = 0; i < daysToShow; i++) {
      const date = new Date(today);
      date.setDate(date.getDate() + i);
      const dateStr = date.toISOString().split('T')[0];

      const numberOfAppointments = Math.floor(Math.random() * 4) + 1;

      for (let j = 0; j < numberOfAppointments; j++) {
        appointments.push({
          date: dateStr,
          patient: patients[Math.floor(Math.random() * patients.length)],
          doctor: doctors[Math.floor(Math.random() * doctors.length)],
          slot: slots[Math.floor(Math.random() * slots.length)]
        });
      }
    }
  });
</script>
<div class="header">
    <a href={`/receptionists`} class="home-button">Home</a>
    <h1>🏥 Welcome to MedCare Portal</h1>
    <button class="logout-button" on:click={() => goto("/")}>Logout</button>
</div>
<div class="grid">
		<div class="card" on:click={() => goto(`/registration`)} tabindex="0">
			<h2 align="center">Patient Registration</h2>
		</div>
<div class="card" on:click={() => goto('/receptionists/doctors')} tabindex="0">
			<h2 align="center">Doctor Availability</h2>
		</div>
</div>

<div class="container">
  <h3>Appointments for the week</h3>


  <div class="scroll-section">
  {#each weekDates as dateStr}
  <div class="day-block">
    <h3>{dateStr}</h3>
    {#each slots as time}
      {#if appointments.find(a => a.date === dateStr && a.slot === time)}
        {#each appointments.filter(a => a.date === dateStr && a.slot === time) as app}
          <div class="appointment-card-button" on:click={() => goto(`/receptionists/bill`)} tabindex="0">
            <p><strong>Time:</strong> {app.slot}</p>
            <p><strong>Patient:</strong> {app.patient}</p>
            <p><strong>Doctor:</strong> {app.doctor}</p>
          </div>
        {/each}
      {:else}
        <div class="appointment-card-button available" on:click={() => goto(`/receptionists/bill`)} tabindex="0">
          <p><strong>Time:</strong> {time}</p>
          <p class="no-appointments">Available</p>
        </div>
      {/if}
    {/each}
  </div>
{/each}



  </div>
</div>

<style>
  .container {
    max-width: 800px;
    margin: 2rem auto;
    font-family: sans-serif;
    padding: 1rem;
  }

  h1 {
    text-align: center;
    color: #007bff;
  }

  .scroll-section::-webkit-scrollbar {
  width: 8px;
}

.scroll-section::-webkit-scrollbar-thumb {
  background-color: #007bff;
  border-radius: 4px;
}

.scroll-section::-webkit-scrollbar-track {
  background: #f1f1f1;
}

  .day-block {
    min-width: 200px;
    background: #f9f9f9;
    border: 0px solid #ccc;
    border-radius: 8px;
    padding: 1rem;
    flex-shrink: 0;
  }

  .no-appointments {
    font-style: italic;
    color: #fff;
  }

  h3 {
    margin-top: 0;
    color: #333;
	text-align: center;
  }
  .appointment-card-button {
	display: inline-block;
	margin: 1.5rem 2rem;
	padding: 0.6rem 1.2rem;
	background-color:rgb(57, 152, 253);
	color: white;
	text-decoration: none;
	border-radius: 6px;
	font-weight: 500;
	transition: background-color 0.3s ease;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.appointment-card-button:hover {
	background-color: #0056b3;
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
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .logout-button:hover {
        background-color: #c82333;
    }

	.grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
		gap: 1.5rem;
		max-width: 1000px;
		margin: 2rem auto;
		padding: 1rem;
	}

	.card {
		background: #f1f1f1;
		border-radius: 10px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		padding: 1.5rem;
		cursor: pointer;
		transition: transform 0.2s;
		outline: none;
	}

	.card:hover, .card:focus {
		transform: translateY(-5px);
		box-shadow: 0 6px 15px rgba(0, 123, 255, 0.2);
	}

	.card h2 {
		margin: 0 0 0.5rem;
		color: #333;
	}

	.card p {
		color: #555;
	}

</style>

