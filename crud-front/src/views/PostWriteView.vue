<template>
  <div class="write-form">
    <v-container>
      <v-row>
        <v-col cols="8">
          <v-text-field v-model="title" label="제목"></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8">
          <v-textarea v-model="content" label="글 내용 작성"></v-textarea>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8" class="text-right">
          <v-btn color="primary" @click="submitPost">완료</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      title: '',
      content: ''
    }
  },
  methods: {
    submitPost() {

      if (this.title === '' || this.content === '') {
        alert("내용을 입력해주세요.");
        return;
      }

      this.requestBody = { // 데이터 전송
        title: this.title,
        content: this.content
      }

      this.$axios.post("/posts", this.requestBody, { headers: { Authorization: localStorage.getItem('token') }}).then((res) => {
        console.log(res)
        alert("글이 작성되었습니다.")
        this.$router.push('/board');
      }).catch((err) => {
        alert(err)
        if (err.message.indexOf('Network Error') > -1 || err.message.indexOf('500') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        } else if (err.message.indexOf('400') > -1) {
          alert("잘못된 요청입니다.")
        }
      })
      // 완료 버튼을 눌렀을 때의 동작 처리
      // 글 작성 로직 등을 여기에 작성


      // 완료 후 필요한 동작 수행

      // 입력값 초기화
      this.title = '';
      this.content = '';
    }
  }
}
</script>