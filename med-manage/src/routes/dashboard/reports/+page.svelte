<script>
  import { goto } from "$app/navigation";

  const reports = [
    { id: 1, title: "Blood Test", date: "2025-05-15" },
    { id: 2, title: "X-Ray Chest", date: "2025-05-20" },
    { id: 3, title: "MRI Brain", date: "2025-05-25" },
  ];

  /**
   * @param {number} id
   */
  function viewReport(id) {
    goto(`/dashboard/reports/${id}`);
  }

  function goBack() {
    goto("/dashboard");
  }
</script>

<div class="header">
  <a href="/dashboard?user_type=Patient" class="home-button">Back</a>
  <h1 class="title">Medical Reports</h1>
  <a href="/" class="logout-button">Logout</a>
</div>
<div class="dashboard">
  <ul class="list">
    {#each reports as report}
      <li style="text-align: center;" on:click={() => viewReport(report.id)}>
        <strong>{report.title}</strong> — {report.date}
      </li>
    {/each}
  </ul>
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
  .list {
    list-style: none;
    padding: 0;
    max-width: 400px;
    margin: 1rem auto;
  }
  .list li {
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 6px;
    margin-bottom: 0.5rem;
    cursor: pointer;
    transition: background 0.2s;
  }
  .list li:hover {
    background-color: #f0f8ff;
  }
  button {
    display: block;
    margin: 2rem auto 0;
    padding: 0.5rem 1rem;
    border: none;
    background-color: #007bff;
    color: white;
    border-radius: 4px;
    cursor: pointer;
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
