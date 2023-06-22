<!-- <template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <v-text-field v-model="id" label="ID"></v-text-field>
        <v-text-field v-model="password" label="Password" type="password"></v-text-field>
      </v-col>
     
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" @click="submitSignUp">가입하기</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-alert v-if="showAlert" type="success" dismissible @click:close="showAlert = false">
          회원가입 되었습니다.
        </v-alert>
      </v-col>
    </v-row>
  </v-container>
</template> -->

<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="4">
        <v-card>
          <v-card-title class="text-center">회원가입</v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field v-model="username" label="ID"></v-text-field>
              <v-text-field v-model="password" label="Password" type="password"></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn color="secondary" @click="submitSignUp">가입하기</v-btn>
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
      password: ''
    }
  },
  methods: {
    submitSignUp() {
      if (this.id === '' || this.password === '') {
        alert("아이디 혹은 패스워드를 입력해주세요.");
        return;
      }

      this.requestBody = { // 데이터 전송
        username: this.username,
        password: this.password
      }

      this.$axios.post("/users/register", this.requestBody).then((res) => {
        console.log(res)
        alert(this.username + "님 가입완료 되었습니다.")
        this.$router.push('/');
      }).catch((err) => {
        console.log(err)
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        } else if (err.message.indexOf('400') > -1) {
          alert("잘못된 요청입니다.")
        } else if (err.message.indexOf('500') > -1) {
          alert("중복 회원 입니다.")
        }
      })
      
    }
  }
}
</script>