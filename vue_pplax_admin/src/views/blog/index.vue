<template>
	<div class="app-container">
	  <!-- 查询和其他操作 -->
	  <div class="filter-container" style="margin: 10px 0 10px 0">
		<el-button
		  class="filter-item"
		  type="primary"
		  @click="handleAdd"
		  icon="el-icon-edit"
		  >添加博客</el-button
		>
	  </div>
  
	  <el-table :data="tableData" style="width: 100%">
		<el-table-column type="selection"></el-table-column>
  
		<el-table-column label="序号" width="60">
		  <template slot-scope="scope">
			<span>{{scope.$index + 1}}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="标题图" width="160">
		  <template slot-scope="scope">
			<!-- <span>{{scope.row.pictureList}}</span> -->
		  </template>
		</el-table-column>
  
		<el-table-column label="标题" width="160">
		  <template slot-scope="scope">
			<span>{{ scope.row.title }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="简介" width="200">
		  <template slot-scope="scope">
			<span>{{ submitStr(scope.row.summary, 30) }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="作者" width="100">
		  <template slot-scope="scope">
			<span>{{ scope.row.user.nickName }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="分类" width="100">
		  <template slot-scope="scope">
			<span>{{ scope.row.blogSort.sortName }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="标签" width="100">
		  <template slot-scope="scope">
			<template>
			  <el-tag
				v-if="item"
				:key="index"
				v-for="(item, index) in scope.row.tagList"
			  >
				<template v-if="item.status == statue.ENABLE">
				  <span>{{item.content}}</span>
				</template>
				<template v-if="item.status == statue.DISABLED">
				  <del>{{item.content}}</del>
				</template>
			  </el-tag>
			</template>
		  </template>
		</el-table-column>
  
		<el-table-column label="点击数" width="80">
		  <template slot-scope="scope">
			<span>{{ scope.row.clickCount }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="收藏数" width="80">
		  <template slot-scope="scope">
			<span>{{ scope.row.collectCount }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="是否原创" width="100">
		  <template slot-scope="scope">
			<template v-if="scope.row.isOriginal == 1">
			  <span>是</span>
			</template>
			<template v-if="scope.row.isOriginal == 0">
			  <span>否</span>
			</template>
		  </template>
		</el-table-column>
  
		<el-table-column label="创建时间" width="160">
		  <template slot-scope="scope">
			<span>{{ scope.row.createTime }}</span>
		  </template>
		</el-table-column>
  
		<el-table-column label="状态" width="100">
		  <template slot-scope="scope">
			<template v-if="scope.row.status == statue.ENABLE">
			  <span>正常</span>
			</template>
			<template v-if="scope.row.status == statue.FREEZE">
			  <span>冻结</span>
			</template>
			<template v-if="scope.row.status == statue.DISABLED">
			  <span>已删除</span>
			</template>
			<template v-if="scope.row.status == statue.STICK">
			  <span>顶置</span>
			</template>
		  </template>
		</el-table-column>
  
		<el-table-column label="操作" fixed="right" min-width="150">
		  <template slot-scope="scope">
			<el-button @click="handleEdit(scope.row)" type="primary" size="small"
			  >编辑</el-button
			>
			<el-button @click="handleDelete(scope.row)" type="danger" size="small"
			  >删除</el-button
			>
		  </template>
		</el-table-column>
	  </el-table>
  
	  <!--分页-->
	  <div class="block">
		<el-pagination
		  @current-change="handleCurrentChange"
		  :current-page.sync="currentPage"
		  :page-size="pageSize"
		  layout="total, prev, pager, next, jumper"
		  :total="total"
		>
		</el-pagination>
	  </div>
  
	  <!-- 添加或修改对话框 -->
	  <el-dialog :title="title" :visible.sync="dialogFormVisible">
		<el-form :model="form">
		  <el-form-item
			v-if="isEditForm == true"
			label="博客UID"
			:label-width="formLabelWidth"
		  >
			<el-input v-model="form.uid" auto-complete="off" disabled></el-input>
		  </el-form-item>
  
		  <el-form-item
			v-if="isEditForm == false"
			label="博客UID"
			:label-width="formLabelWidth"
			style="display: none"
		  >
			<el-input v-model="form.uid" auto-complete="off"></el-input>
		  </el-form-item>
  
		  <el-form-item label="标题图" :label-width="formLabelWidth">
			<el-input v-model="form.fileUid" auto-complete="off"></el-input>
		  </el-form-item>
  
		  <el-form-item label="标题" :label-width="formLabelWidth" required>
			<el-input v-model="form.title" auto-complete="off"></el-input>
		  </el-form-item>
  
		  <el-form-item label="简介" :label-width="formLabelWidth" required>
			<el-input v-model="form.summary" auto-complete="off"></el-input>
		  </el-form-item>
  
		  <el-form-item label="内容" :label-width="formLabelWidth" required>
			<el-input v-model="form.content" auto-complete="off"></el-input>
		  </el-form-item>
  
		  <el-form-item label="标签" :label-width="formLabelWidth" required>
			<el-select
			  v-model="tagValue"
			  multiple
			  size="small"
			  placeholder="请选择"
			  filterable
			>
			  <el-option
				v-for="item in tagData"
				:key="item.uid"
				:label="item.content"
				:value="item.uid"
			  >
			  </el-option>
			</el-select>
			<!--<p v-if="labelValue.length > 2" style="color: red;">最多选择两个标签</p>-->
		  </el-form-item>
  
		  <el-form-item label="点击数" :label-width="formLabelWidth" required>
			<el-input v-model="form.clickCount" auto-complete="off"></el-input>
		  </el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
		  <el-button @click="dialogFormVisible = false">取 消</el-button>
		  <el-button type="primary" @click="submitForm">确 定</el-button>
		</div>
	  </el-dialog>
	</div>
  </template>
  
  <script>
   import { getBlogList, addBlog, editBlog, deleteBlog } from "@/api/blog";
   import EStatus from "../../base/EStatus";
   import ResponseCode from '../../base/ResponseCode';
   import { formatData } from '@/utils/webUtils'
   export default {
  data() {
	return {
		  tableData: [], //博客数据
		  tagData: [], //标签数据
		  tagValue: [], //保存选中标签id(编辑时)
		  keyword: "",
		  currentPage: 1,
		  pageSize: 10,
		  statue: EStatus,
		  total: 0, //总数量
		  title: "增加博客",
		  dialogFormVisible: false, //控制弹出框
		  formLabelWidth: '120px',
		  isEditForm: false ,
		  form: {
		  uid: null,
		  title: null,
		  summary: null,
		  content: null,
		  tagUid: null,
		  fileUid: null,
	  }
	};
  },
  created() {
	var that = this;
  
	console.log(ResponseCode)
	// 请求获得博客列表
	var params = new URLSearchParams();
	params.append("keyword", this.keyword);
	params.append("currentPage", this.currentPage);
	params.append("pageSize", this.pageSize);
	getBlogList(params).then(response => {
	  this.tableData = response.data.records;
	  this.currentPage = response.data.current;
	  this.pageSize = response.data.size;
	  this.total = response.data.total;
  
		  if (response.code == ResponseCode.success.code) {
			  this.tableData = response.data.records;
			  this.currentPage = response.data.current;
			  this.pageSize = response.data.size;
			  this.total = response.data.total;
		  } else {
			this.$message({
			  type: "error",
			  message: response.message
			});
		  }
	  });
  },
  methods: {
		blogList: function(params) {
			var params = new URLSearchParams();
			params.append("keyword", this.keyword);
			params.append("currentPage", this.currentPage);
			params.append("pageSize", this.pageSize);
			getBlogList(params).then(response => {
				this.tableData = response.data.records;
				this.currentPage = response.data.current;
				this.pageSize = response.data.size;
				this.total = response.data.total;
			});
		},
		getFormObject: function() {
		  var formObject = {
				  uid: null,
		  title: null,
		  summary: null,
		  content: null,
		  tagUid: null,
		  fileUid: null,
		  };
		  return formObject;
	  },
		submitStr: function(str, index) {
			if(str.length > index) {
				return str.slice(0, index) + "...";
			}
			return str;
		},
	handleAdd: function() {
	  console.log("点击了添加博客");
			this.dialogFormVisible = true;
			this.form = this.getFormObject();
			this.tagValue = [];
			this.isEditForm = false;
	},
	handleEdit: function(row) {
			console.log("点击了编辑");
			this.form = row;
			this.tagValue = [];
			var json = row.tagList;
	  for (var i = 0, l = json.length; i < l; i++) {
		if (json[i] != null) {
		  this.tagValue.push(json[i]["uid"]);
		}
			}
			console.log(this.tagValue, "编辑的tagValue");
			this.dialogFormVisible = true;
			this.isEditForm = true;
  
	},
	handleDelete: function(row) {
			console.log("点击了删除");
			var that = this;
			let params = new URLSearchParams();
			params.append("uid", row.uid);
			deleteBlog(params).then(response=> {
					console.log(response);
					this.$message({
			  type: "success",
			  message: response.data
		  });
					that.blogList();
			})
		},
		handleCurrentChange: function(val) {
	  console.log("点击了换页");
	  this.currentPage = val;
	  this.blogList();
	},
		submitForm: function() {
  
			this.form.tagUid = this.tagValue.join(",");
			console.log(this.form);
			var params = formatData(this.form);
			console.log("点击了提交表单", params);
			if(this.isEditForm) {
				editBlog(params).then(response=> {
						console.log(response);
						if(response.code == "success") {
							this.$message({
								type: "success",
								message: response.data
							});
						} else {
							this.$message({
								type: "error",
								message: response.data
				});
						}
  
						this.dialogFormVisible = false;
						this.blogList();
				})
			} else {
				addBlog(params).then(response=> {
						console.log(response);
						this.$message({
			  type: "success",
			  message: response.data
			});
						this.dialogFormVisible = false;
						this.blogList();
				})
  
			}
  
		},
  
  
  }
   };
  </script>
  