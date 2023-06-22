<template>
  <div class="post-detail">
    <v-container>
      <v-row>
        <v-col cols="8">
          <v-row>
            <v-col cols="4">
              <v-text-field v-model="id" label="글 번호" readonly></v-text-field>
            </v-col>
            <v-col cols="8">
              <v-text-field v-model="username" label="작성자" readonly></v-text-field>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8">
          <v-text-field v-model="title" label="제목" readonly></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8">
          <v-textarea v-model="content" label="글 내용" readonly></v-textarea>
        </v-col>
      </v-row>
      <v-row v-if="canEdit">
          <v-col cols="8" class="text-right">
             
            </v-col>
          <v-col cols="8" class="text-right">
            <v-btn color="primary" @click="editPost" class="mr-2">수정</v-btn>
            <v-btn color="primary" @click="deletePost">삭제</v-btn>
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
      content: '',
      username: '',
      id: '',
      canEdit: false

    }
  },
  mounted() {
    this.id = this.$route.query.id;
    this.getPostDetail()
  },
  methods: {
    getPostDetail() {


      this.$axios.get("/posts/" + this.id, { headers: { Authorization: localStorage.getItem('token') } }).then((res) => {
        this.id = res.data.id
        this.username = res.data.username
        this.title = res.data.title
        this.content = res.data.content
        this.$axios.get("/users/myinfo", { headers: { Authorization: localStorage.getItem('token') } }).then((res) => {
          if (this.username == res.data.username) {
             this.canEdit = true;
          }

        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1 || err.message.indexOf('500') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          } else if (err.message.indexOf('400') > -1) {
            alert("잘못된 요청입니다.")
          }
        })
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1 || err.message.indexOf('500') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        } else if (err.message.indexOf('400') > -1) {
          alert("잘못된 요청입니다.")
        }
      })

      this.title = '';
      this.content = '';
    },
    editPost() {
      this.$router.push({ path: '/edit', query: { id: this.id } })
    },
    deletePost() {
      this.$axios.delete("/posts/" + this.id, { headers: { Authorization: localStorage.getItem('token') } }).then(() => {
        alert("삭제되었습니다")
        this.$router.push('/board')
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1 || err.message.indexOf('500') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        } else if (err.message.indexOf('400') > -1) {
          alert("잘못된 요청입니다.")
        }
      })
    }
  }
}
</script>