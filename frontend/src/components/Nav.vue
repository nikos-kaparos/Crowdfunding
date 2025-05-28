  <template>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand">Home</router-link>

        <div>
          <ul class="navbar-nav me-auto mb-2 mb-md-0" v-if="!auth">
            <li class="nav-item">
              <router-link to="/login" class="nav-link">Login</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/register" class="nav-link">Register</router-link>
            </li>
          </ul>
  
          <ul class="navbar-nav me-auto mb-2 mb-md-0" v-if="auth">
            <li class="nav-item">
              <router-link to="/admin" class="nav-link" v-if="authority == 'ROLE_ADMIN'">Users</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/admin-projects" class="nav-link"  v-if="authority == 'ROLE_ADMIN'">Admin Project</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/new-projects" class="nav-link"  v-if="authority == 'CREATOR'">New Project</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/creator" class="nav-link" v-if="authority == 'CREATOR'">My Projects</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/projects" class="nav-link">Project</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/supporters" class="nav-link">Supporters</router-link>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link" @click="logout">Logout</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </template>
  
  <script lang="ts">
  import {computed} from 'vue';
  import {useStore} from "vuex";
  import { useRouter } from "vue-router";
  import { jwtDecode } from 'jwt-decode';
  
  export default {
    name: "Nav",
    setup() {
      const store = useStore();
  
      const auth = computed(() => store.state.authenticated);

      const token = localStorage.getItem("accessToken");
      
      const authority = computed(() => {
        if (token) {
          try {
            const decodedToken: any = jwtDecode(token); // Αποκωδικοποιούμε το JWT
            return decodedToken?.authorities?.[0]?.authority || null; // Επιστρέφουμε το authority
          } catch (error) {
            console.error("Invalid token:", error);
            return null;
          }
        }
        return null; // Αν δεν υπάρχει token, επιστρέφουμε null
      });

      const router = useRouter();

      const logout = async () => {
        localStorage.removeItem("accessToken");
        window.location.href ='/login'; 
      }
      return {
        auth,
        authority,
        logout
      }
    }
  }
  </script>