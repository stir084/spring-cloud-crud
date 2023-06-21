<template>
  <div class="board-list">
    <v-container>
      <v-row>
        <v-col cols="8">
          <v-card class="elevation-1">
            <v-card-text>
              <v-data-table :headers="headers" :items="list" item-key="idx" :footer-props="{
                'items-per-page-text': '페이지 당 보여질 갯수',
              }" :header-align="'left'" :align="'left'">
                <template v-slot:item="{ item }">
                  <tr>
                    <td>{{ item.id }}</td>
                    <td><a v-on:click="fnView(item.id)">{{ item.title }}</a></td>
                    <td>{{ item.username }}</td>
                    <td>{{ item.createdAt }}</td>
                  </tr>
                </template>
              </v-data-table>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8" class="text-right">
          <v-btn color="primary" @click="fnMyInfo" class="mr-2">
              내 정보 조회
          </v-btn>
          <v-btn color="primary" @click="fnWrite">
            글쓰기
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      headers: [
        { text: 'No', value: 'id' },
        { text: '제목', value: 'title' },
        { text: '작성자', value: 'username' },
        { text: '등록일시', value: 'createdAt' },
      ],
      list: [],
      requestBody: {}, //리스트 페이지 데이터전송
      no: '', //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 1,
      size: this.$route.query.size ? this.$route.query.size : 10,
      keyword: this.$route.query.keyword,
      paginavigation: function () { //페이징 처리 for문 커스텀
        let pageNumber = [] //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
        keyword: this.keyword,
        page: this.page,
        size: this.size
      }
      this.$axios.get("/posts", {
        headers: { Authorization: localStorage.getItem('token') }
      }).then((res) => {
        console.log(res.data[0])
        this.list = res.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnWrite() {
      this.$router.push('/write');      
    },
    fnView(id) {
      this.$router.push({ path: '/detail', query: { id: id } })
    },
    fnMyInfo() {
      this.$router.push('/myInfo')
    }
  }
}
</script>