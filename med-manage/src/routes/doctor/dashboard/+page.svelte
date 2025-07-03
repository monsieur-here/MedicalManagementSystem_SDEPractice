<script>
  import { DOCTORS_ROUTES } from "$lib/routes";
  import { onMount } from "svelte";
  import { getPrescription } from "../../../apis/doctor/prescription";
  import { BASE_API_URL } from "$lib/config";

  let page = 1;
  let pageSize = 20;

  let prescriptionData = [];

  onMount(() => {
    const getAPITrigger = async () => {
      const requestOptions = {
        method: "GET",
        redirect: "follow",
      };

      try {
        const response = await fetch(
          BASE_API_URL + "/patient/prescription?pageNo=1&pageSize=20",
          requestOptions
        );

        const result = await response.json();

        prescriptionData = result;
      } catch (error) {
        console.error(error);
        return error;
      }

      // prescriptionData = await getPrescription({
      //   pageNo: page,
      //   pageSize,
      // });
    };

    getAPITrigger();
  });

  console.log("====", prescriptionData);
</script>

<div class="header">
  <a href={DOCTORS_ROUTES.dashboard.url} class="home-button">Back</a>
  <h1 class="title">🩺 Doctor Dashboard</h1>
  <button class="logout-button" on:click={handleLogOut}>Logout</button>
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
</style>
