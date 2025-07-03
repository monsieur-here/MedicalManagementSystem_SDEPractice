<script>
  import { PATIENT_ROUTES } from "$lib/routes";
  import { onMount } from "svelte";

  const title = PATIENT_ROUTES.dashboard.title;
  const description = PATIENT_ROUTES.dashboard.description;

  onMount(() => {});
</script>

<svelte:head>
  <title>{title}</title>
</svelte:head>

<div>
  <p>{description}</p>
</div>
