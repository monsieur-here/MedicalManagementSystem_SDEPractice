<script>
  import { page } from "$app/stores";
  import { goto } from "$app/navigation";

  let name = "";
  let date = "";
  let reason = "";
  let specialist = "";

  // Get data from URL params reactively
  $: {
    const params = $page.url.searchParams;
    name = params.get("name") ?? "";
    date = params.get("date") ?? "";
    reason = params.get("reason") ?? "";
    specialist = params.get("specialist") ?? "";

    console.log({ name, date, reason, specialist });
  }

  function goBackToDashboard() {
    goto("/dashboard");
  }
</script>

<div class="header">
  <a href="/dashboard?user_type=Patient" class="home-button">Home</a>
  <a href="/" class="logout-button">Logout</a>
</div>
{#if name}
  <h2 style="text-align: center;">Appointment Confirmed ✅</h2>

  <div class="confirmation">
    <p><strong>Patient:</strong> {name}</p>
    <p><strong>Date:</strong> {date}</p>
    <p><strong>Reason:</strong> {reason}</p>
    <p><strong>Specialist:</strong> {specialist}</p>

    <button on:click={goBackToDashboard}>← Back to Dashboard</button>
  </div>
{:else}
  <p>Loading or missing data...</p>
{/if}

<style>
  .confirmation {
    max-width: 500px;
    margin: 2rem auto;
    padding: 1.5rem;
    background: #f0f8ff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  }

  button {
    margin-top: 1.5rem;
    padding: 0.5rem 1rem;
    border: none;
    background-color: #007bff;
    color: white;
    border-radius: 4px;
    cursor: pointer;
    display: block;
    margin-left: auto;
    margin-right: auto;
  }
  button:hover {
    background-color: #2b8df7;
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
