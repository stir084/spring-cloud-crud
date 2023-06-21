<template>
  <div class="my-info-form">
    <v-container>
      <v-row>
        <v-col cols="12" class="text-center">
          <h2>내 정보 조회</h2>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="6">
          <v-text-field v-model="username" label="아이디" readonly></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field v-model="postCount" label="내가 쓴 글의 개수" readonly></v-text-field>
        </v-col>
      </v-row>
     
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      postCount: 0
    }
  },
  mounted() {
    // 내 정보 조회 로직을 수행하여 데이터를 가져온다고 가정


     this.$axios.get("/users/myinfo", { headers: { Authorization: localStorage.getItem('token') } }).then((res) => {

      this.username = res.data.username
      this.postCount = res.data.postCount
    }).catch((err) => {
      if (err.message.indexOf('Network Error') > -1 || err.message.indexOf('500') > -1) {
        alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
      } else if (err.message.indexOf('400') > -1) {
        alert("잘못된 요청입니다.")
      }
    })
  }
}
</script>

<style scoped>
.my-info-form {
  margin-top: 20px;
}
</style>