<template>
  <form @submit.prevent="submit">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <input v-model="data.username" type="text" class="form-control" id="floatingInput" placeholder="username">
    <label for="floatingInput">Username</label>
    
    <input v-model="data.password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
    <label for="floatingPassword">Password</label>

    <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-body-secondary">&copy; DS 2024–2025</p>
  </form>
  <!-- Εμφάνιση σφάλματος -->
  <p v-if="error.message" style="color: red;  font-weight: bold;">{{ error.message }}</p>
</template>

<script>
import { reactive } from 'vue';
import { useRouter } from "vue-router";

export default {
  name: "Login",
  setup() {
    const data = reactive({
      username: '',
      password: ''
    });

    const router = useRouter();
    const error = reactive({ message: '' });
    const submit = async () => {
  try {
    console.log(data);

    // Αποστολή αίτησης για login
    const response = await fetch('api/auth/signin', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });

    // Έλεγχος για την επιτυχία της αίτησης
    if (!response.ok) {
            const errorData = await response.json(); // Διαβάζουμε την απάντηση JSON
            error.message = errorData.message || 'Registration failed'; // Χρησιμοποιούμε το μήνυμα σφάλματος από το backend
            throw new Error(error.message); // Ρίχνουμε το σφάλμα για να εμφανιστεί στον χρήστη
    }
    // Αν η αίτηση είναι επιτυχής, ανακατευθύνουμε τον χρήστη
    const responseData = await response.json();
    localStorage.setItem('accessToken', responseData.accessToken);
    await router.push('/');
  } catch (err) {
    console.error("Error during login:", err); // Δείτε το σφάλμα εδώ
    error.message = err.message; // Ορισμός του μηνύματος σφάλματος
  }
};

    return {
      data,
      error,
      submit
    };

  }
}
</script>

<style>
/* Προαιρετικά στυλ για το UI */
</style>
