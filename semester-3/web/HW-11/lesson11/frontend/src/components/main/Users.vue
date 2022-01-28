<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in users" :key="user.id">
                <td :key="user.id">{{ user.id }}</td>
                <td :key="user.name">{{ user.name }}</td>
                <td :key="user.login">{{ user.login }}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: "Users",
    data: function () {
        return {
            users: []
        }
    },
    beforeCreate() {
        axios.get('/api/1/users').then(response => {
            this.users = response.data
        })
    }
}
</script>

<style scoped>
</style>