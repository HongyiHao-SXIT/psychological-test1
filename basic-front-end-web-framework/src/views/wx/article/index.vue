<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
            style="float:right"
            type="primary"
            @click="handleSearchList()"
            size="small">
          查询搜索
        </el-button>
        <el-button
            style="float:right;margin-right: 15px"
            @click="handleResetSearch()"
            size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.keyword" class="input-width" placeholder="标题、内容" clearable></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="roleTable"
                :data="list"
                style="width: 100%;"
                v-loading="listLoading" border>
        <el-table-column label="文章编号" align="center">
          <template slot-scope="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column label="文章标题" align="center">
          <template slot-scope="scope">{{ scope.row.title }}</template>
        </el-table-column>
        <el-table-column label="文章封面" align="center">
          <template slot-scope="scope">
            <el-image :preview-src-list="[...scope.row.imageUrl.split(',').map((item)=>baseUrl+'/files/'+item)]"
                      :src="baseUrl+'/files/'+scope.row.imageUrl.split(',')[0]"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="阅读量" align="center">
          <template slot-scope="scope">{{ scope.row.readNum }}</template>
        </el-table-column>
        <el-table-column label="创建时间" width="160" align="center">
          <template slot-scope="scope">{{ scope.row.createdDatetime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       @click="handleUpdate(scope.$index, scope.row)">编辑
            </el-button>
            <el-button size="mini"
                       type="text"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          layout="total, sizes,prev, pager, next,jumper"
          :current-page.sync="listQuery.pageNum"
          :page-size="listQuery.pageSize"
          :page-sizes="[5,10,15]"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog
        :title="isEdit?'编辑':'添加'"
        :visible.sync="dialogVisible"
        width="70%">
      <el-form :model="massageItem"
               ref="roleForm"
               label-width="150px" size="small">
        <el-form-item label="文章标题">
          <el-input v-model="massageItem.title" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="文章封面">
          <el-upload
              class="avatar-uploader"
              :action="baseUrl+'/upload/file/upload'"
              list-type="picture-card"
              :file-list="massageItem.imageUrlList"
              :multiple="false"
              :limit="1"
              :headers="headers"
              :on-success="(res,file,fileList)=>{handleAvatarSuccess(res,file,fileList,'imageUrl')}"
              :on-remove="(file, fileList)=>{return handleRemove(file, fileList, 'imageUrl')}"
              :before-upload="beforeAvatarUpload">
            <i style="font-size:40px" class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章详情">
          <tinymce style="width: 80%" ref="contentTinymce" @input="contentReserve"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {fetchList, deleteById, saveOrUpdate} from '@/api/wxArticle';
import {formatDate} from '@/utils/date';
import Tinymce from "@/components/Tinymce"
import {getToken} from '@/utils/auth'


const defaultListQuery = {
  pageNum: 1,
  pageSize: 5,
  keyword: null
};
const defaultMassageItem = {
  id: null,
  imageUrl: '',
  imageUrlList: [],
  orderInstructionsZh: '',
  orderInstructionsEn: ''


};
export default {
  name: 'roleList',
  components: {Tinymce},
  data() {
    return {
      listQuery: Object.assign({}, defaultListQuery),
      list: null,
      total: null,
      listLoading: false,
      dialogVisible: false,
      massageItem: Object.assign({}, defaultMassageItem),
      isEdit: false
    }
  },
  computed: {
    baseUrl() {
      return process.env.BASE_API
    },
    headers() {
      return {
        Authorization: getToken()
      }
    }
  },
  created() {
    this.getList();
  },
  filters: {
    formatDateTime(time) {
      if (time == null || time === '') {
        return 'N/A';
      }
      let date = new Date(time);
      return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
    }
  },
  methods: {
    handleAvatarSuccess(res, file,fileList, filedName) {
      this.massageItem[filedName+'List'] = fileList
      this.massageItem[filedName] = fileList.map((item)=>item.response.data).join(',')
    },
    handleRemove(file, filelist, filedName) {
      this.massageItem[filedName] = filelist.map(item => item.name).join(',')
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG、PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return isJPG && isLt2M;
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
    },
    handleSearchList() {
      this.listQuery.pageNum = 1;
      this.getList();
    },
    handleSizeChange(val) {
      this.listQuery.pageNum = 1;
      this.listQuery.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val;
      this.getList();
    },
    handleAdd() {
      this.dialogVisible = true;
      this.isEdit = false;
      this.massageItem = Object.assign({}, defaultMassageItem);
      this.massageItem.imageUrlList = []
      setTimeout(() => {
        this.$refs.contentTinymce.setContent('')
      }, 1000)
    },
    handleDelete(index, row) {
      this.$confirm('是否要删除该数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById({id:row.id}).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getList();
        });
      });
    },
    handleUpdate(index, row) {
      this.dialogVisible = true;
      this.isEdit = true;
      this.massageItem = Object.assign({}, row);
      this.massageItem.imageUrlList = row.imageUrl.split(',').map(item => {
        return {
          name: item,
          url: this.baseUrl + '/files/' + item,
          response: {
            data: item
          }
        }
      })
      setTimeout(() => {
        this.$refs.contentTinymce.setContent(row.content)
      }, 1000)
    },
    handleDialogConfirm() {
      this.$confirm('是否要确认?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.isEdit) {
          saveOrUpdate(this.massageItem).then(response => {
            this.$message({
              message: '修改成功！',
              type: 'success'
            });
            this.dialogVisible = false;
            this.getList();
          })
        } else {
          saveOrUpdate(this.massageItem).then(response => {
            this.$message({
              message: '添加成功！',
              type: 'success'
            });
            this.dialogVisible = false;
            this.getList();
          })
        }
      })
    },
    handleSelectMenu(index, row) {
      this.$router.push({path: '/ums/allocMenu', query: {roleId: row.id}})
    },
    handleSelectResource(index, row) {
      this.$router.push({path: '/ums/allocResource', query: {roleId: row.id}})
    },
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.listLoading = false;
        this.list = response.data.list;
        this.total = response.data.total;
      });
    },
    contentReserve(e) {
      this.massageItem.content = e
    },
    handleSetType(index, row) {
      // 跳转到场馆类型设置
      this.$router.push({
        path: '/wx/gymnasium-type',
        query: {
          id: row.id
        }
      })
    }
  }
}
</script>
<style></style>


