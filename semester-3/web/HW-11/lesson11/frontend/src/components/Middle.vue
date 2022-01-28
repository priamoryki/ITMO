<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        Register,
        Enter,
        Index,
        Sidebar,
        Users
    },
    props: ["posts"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
