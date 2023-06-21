<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="4">
        <v-card>
          <v-card-title class="text-center">로그인</v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field v-model="username" label="ID"></v-text-field>
              <v-text-field v-model="password" label="Password" type="password"></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn color="primary" @click="login">로그인</v-btn>
            <v-btn color="secondary" @click="goToSignUp">회원가입</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    login() {
      this.requestBody = { // 데이터 전송
        username: this.username,
        password: this.password
      }
      
      this.$axios.post("/users/login", this.requestBody).then((res) => {
        localStorage.setItem('token', res.data.token);
        alert("로그인에 성공하였습니다.")
        this.$router.push('/board');
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        } else if (err.message.indexOf('400') > -1) {
          alert("잘못된 요청입니다.")
        } else if (err.message.indexOf('401') > -1) {
          alert("회원정보가 존재하지 않습니다.")
        }
      })
    },
    goToSignUp() {
      this.$router.push('/register');
    },
  },
};
</script>