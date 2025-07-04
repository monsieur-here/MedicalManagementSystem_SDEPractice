<script>
  let isOpen = false;

  let prescriptionName = "";
  let medication = "";
  let dosage = "";
  let frequency = "";
  let criticality = "";

  let loading = "";

  function toggleBox() {
    isOpen = !isOpen;
  }
</script>

<button class="toggle-btn" on:click={toggleBox}>
  {isOpen ? "Hide" : "Show"} Details
</button>

{#if isOpen}
  <div class="box">
    <form
      on:submit|preventDefault={handleLogin}
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
    </form>
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
</style>
