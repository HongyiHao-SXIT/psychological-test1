<template> 
  <div class="app-container">
    <div class="table-container">
      <el-form :model="admin"
               ref="adminForm"
               label-width="150px" size="small">
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              :action="baseUrl+'/upload/file/upload'"
              list-type="picture-card"
              :file-list="admin.iconList"
              :multiple="false"
              :limit="1"
              :headers="headers"
              :on-success="(res,file,fileList)=>{handleAvatarSuccess(res,file,fileList,'icon')}"
              :on-remove="(file, fileList)=>{return handleRemove(file, fileList, 'icon')}"
              :before-upload="beforeAvatarUpload">
            <i style="font-size:40px" class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="admin.nickName" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱：">
          <el-input v-model="admin.email" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="密码：">
          <el-input v-model="admin.password"  type="password" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="admin.note"
                    type="textarea"
                    :rows="5"
                    style="width: 250px"></el-input>
        </el-form-item>
      </el-form>
      <div>
        <el-row>
          <el-col :offset="5" :span="3">
            <el-button type="primary" @click="handleDialogConfirm()" size="small">保存</el-button>
          </el-col>
        </el-row>
      </div>

    </div>
    <el-dialog
        :title="isEdit?'编辑用户':'添加用户'"
        :visible.sync="dialogVisible"
        width="40%">

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
        title="分配角色"
        :visible.sync="allocDialogVisible"
        width="30%">
      <el-select v-model="allocRoleIds" multiple placeholder="请选择" size="small" style="width: 80%">
        <el-option
            v-for="item in allRoleList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allocDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleAllocDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {fetchList,getAdminInfo,updateAdmin,} from '@/api/login';
import {formatDate} from '@/utils/date';
import {getToken} from '@/utils/auth'
const defaultListQuery = {
  pageNum: 1,
  pageSize: 10,
  keyword: null
};
const defaultAdmin = {
  id: null,
  username: null,
  password: null,
  nickName: null,
  email: null,
  note: null,
  status: 1,
  idNumber:'',
  gender:'',
  phone:'',
  iconList:[],
  icon:''
};
export default {
  name: 'adminList',
  data() {
    return {
      listQuery: Object.assign({}, defaultListQuery),
      list: null,
      total: null,
      listLoading: false,
      dialogVisible: false,
      admin: Object.assign({}, defaultAdmin),
      isEdit: false,
      allocDialogVisible: false,
      allocRoleIds:[],
      allRoleList:[],
      allocAdminId:null,
      options:[{
        value:'a',
        label:'男'
      },{
        value:'b',
        label:'女'
      }]
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
      this.admin = Object.assign({},defaultAdmin);
    },
    handleUpdate(index, row) {
      this.dialogVisible = true;
      this.isEdit = true;
      this.admin = Object.assign({},row);
    },
    handleDialogConfirm() {
      this.$confirm('是否要确认?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateAdmin(this.admin.id,this.admin).then(response => {
          this.$message({
            message: '修改成功！',
            type: 'success'
          });
          this.dialogVisible =false;
          this.getList();
        })
      })
    },
    getList() {
      this.listLoading = true;
      getAdminInfo().then((res)=>{
        this.admin=res.data || {}
        this.admin.iconList = [{
          name: this.admin.icon,
          url: this.baseUrl + '/files/' + this.admin.icon,
          response:{
            data: this.admin.icon
          }
        }]
      })
    },
    handleAvatarSuccess(res, file,fileList, filedName) {
      this.admin[filedName+'List'] = fileList
      this.admin[filedName] = fileList.map((item)=>item.response.data).join(',')
    },
    handleRemove(file, filelist, filedName) {
      this.admin[filedName] = filelist.map(item => item.name).join(',')
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

  }
}
</script>
<style></style>


