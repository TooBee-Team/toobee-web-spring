<template>
  <div>
    <h3>项目工程创建</h3>
    <form id="projectForm">
      <label for="identifier">项目标识符: </label>
      <input type="text" id="identifier" name="identifier" placeholder="请输入项目标识符" required>
      <span style="color: red;"> *</span>
      <br/>        
      <label for="name">项目名称: </label>
      <input type="text" id="name" name="name" placeholder="请输入项目名称" required>
      <span style="color: red;"> *</span>
      <br/>        
      <label for="type">项目类型: </label>
      <select type="text" id="type" name="type" placeholder="请选择项目类型">
        <option value="machine">机器</option>
        <option value="building">建筑</option>
        <option value="settlement">settlement</option>
        <option value="recreation">娱乐</option>
        <option value="landscape">景观</option>
        <option value="picture">相片</option>
        <option value="sculpture">雕塑</option>
        <option value="unknown">未知</option>
      </select>
      <br/>        
      <label for="projectCreateTime">项目创建时间: </label>
      <input type="date" id="projectCreateTime" name="projectCreateTime" placeholder="请输入项目创建时间">
      <br/>        
      <label for="projectUpdateTime">项目更新时间: </label>
      <input type="date" id="projectUpdateTime" name="projectUpdateTime" placeholder="请输入项目更新时间">
      <br/>        
      <label for="introduction">项目介绍: </label>
      <input type="text" id="introduction" name="introduction" placeholder="请输入项目介绍">
      <br/>        
      <label for="thumbnail">项目图片: </label>
      <input type="file" id="thumbnail" name="thumbnail" accept="image/*" placeholder="请上传图片">
      <br/>        
      <label for="world">项目纬度: </label>
      <select type="text" id="world" name="world" placeholder="请输入项目纬度" required>
        <option value="overworld">主世界</option>
        <option value="the_nether">下界</option>
        <option value="the_end">末地</option>
      </select>
      <span style="color: red;"> *</span>
      <br/>        
      <label for="x">X 轴坐标:</label>
      <input type="text" id="x" name="x" placeholder="请输入项目X轴坐标" required>
      <span style="color: red;"> *</span>
      <br/>
      <label for="y">Y 轴坐标:</label>
      <input type="text" id="y" name="y" placeholder="请输入项目Y轴坐标" required>
      <span style="color: red;"> *</span>
      <br/>
      <label for="z">Z 轴坐标:</label>
      <input type="text" id="z" name="z" placeholder="请输入项目Z轴坐标" required>
      <span style="color: red;"> *</span>
      <br/>
      <button type="submit">保存项目</button>
    </form>
    
  </div>
</template>
<script>
import axios from 'axios';
export default {
  name: 'CreateProject',
  mounted() {
    const form = document.getElementById('projectForm');
    if (form) {
      form.addEventListener('submit', this.handleSubmit);
    } else {
      console.error('表单未找到');
    }
  },
  methods: {
    async handleSubmit(e) {
      e.preventDefault();
      try {
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData);

        await axios.post('http://localhost:5173/project/create', data, {
          headers: { 'Content-Type': 'application/json' }
        });

        alert('项目保存成功！');
      } catch (error) {
        console.error('提交失败:', error);
        alert('保存失败');
      }
    }
  }
}
</script>
<style scoped>
</style>
