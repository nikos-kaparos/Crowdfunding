<template>
  <div>
    <p v-if="loading">Loading...</p>
    <p v-else-if="message">{{ message}}
    </p>
    <p v-else>Error: No message received.</p>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeMount } from 'vue';
import { useStore } from 'vuex';

export default {
  setup() {
    const message = ref(null);
    const loading = ref(true);
    const token = localStorage.getItem('accessToken');
    const store = useStore();

    onBeforeMount(() => {
      const url = new URL(window.location.href);
      // If the URL does not have our custom flag, then trigger a reload with it.
      if (!url.searchParams.get('reloaded')) {
        url.searchParams.set('reloaded', 'true');
        window.location.replace(url.toString());
      } else {
        // Remove the flag so that future fresh navigations will trigger a reload again.
        url.searchParams.delete('reloaded');
        window.history.replaceState({}, document.title, url.toString());
      }
    });

    onMounted(async () => {
      if (token) {
        try {
          const response = await fetch('http://localhost:8080/api/home', {
            method: 'GET', // Προκαθορισμένη μέθοδος, αλλά καλό είναι να την ορίζουμε ρητά
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json' // Συνήθως χρειάζεται και αυτό
            },
          });

          if (!response.ok) { // Έλεγχος για HTTP σφάλματα (4xx, 5xx)
            const errorText = await response.text(); // Πάρε το μήνυμα σφάλματος από το backend
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }

          const data = await response.text(); // Αν περιμένουμε απλό κείμενο
          message.value = data;
          await store.dispatch('setAuth', true);

        } catch (error) {
          console.error("Error fetching data:", error);
          message.value = "Error fetching data.";
          await store.dispatch('setAuth', false);
        }
      } else {
        message.value = "No token found.";
      }
      loading.value = false;

    });

    return { message, loading };
  },
};
</script>