<template>
  <form @submit.prevent="submit">
    <h1 class="h3 mb-3 fw-normal">Please register in</h1>

    <label for="floatingInput">Username</label>
    <input v-model="data.username" type="text" class="form-control" id="floatingInput" placeholder="username">
    
    <label for="floatingPassword">Password</label>
    <input v-model="data.password" class="form-control" id="floatingPassword" placeholder="Password">

    <label for="floatingEmail">Email</label>
    <input v-model="data.email" type="email" class="form-control" id="floatingEmails" placeholder="email@exmpal.com">
    
    <label for="floatingRole" placeholder="Role">Choose your Role</label>
    <select v-model="data.role" name="role" class="form-control" id="role">
      <option value="creator">Creator</option>
      <option value="supporter">Supporter</option>
    </select>

    <button class="btn btn-primary w-100 py-2" type="submit">Register</button>
    <p class="mt-5 mb-3 text-body-secondary">&copy; DS 2024–2025</p>
  </form>

  <!-- Εμφάνιση σφάλματος -->
  <p v-if="error.message" style="color: red;">{{ error.message }}</p>
</template>

<script lang="ts">
  import { reactive } from 'vue';
  import { useRouter } from "vue-router";

  export default {
    name: "Register",
    setup() {
      const data = reactive({
        username: '',
        password: '',
        email: '',
        role: ''
      });

      const router = useRouter();
      const error = reactive({ message: '' });

      const submit = async () => {
        try {
          console.log(data);

          const response = await fetch('http://localhost:8080/api/auth/signup', { 
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

          // Καθαρίζουμε το σφάλμα και ανακατευθύνουμε τον χρήστη
          error.message = '';
          await router.push('/login');
        } catch (err: any) {
          // Διαχείριση σφαλμάτων και εμφάνιση του μηνύματος
          console.error("Error during registration:", err);
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
  /* Μπορείτε να προσθέσετε στυλ για το UI */
</style>
