<script>
  import AuthWrapper from "$lib/components/AuthWrapper.svelte";
  import ProtectedRoute from "$lib/components/ProtectedRoute.svelte";
</script>

<ProtectedRoute>
  <div>
    <p>Patient | Dashboard</p>
  </div>
</ProtectedRoute>
