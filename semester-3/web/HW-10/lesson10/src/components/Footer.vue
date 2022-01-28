<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2021 by Mike Mirzayanov
        <div>Total users: {{ userCount }}</div>
        <div>Total posts: {{ postsCount }}</div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["userCount", "postsCount"]
}
</script>

<style scoped>

</style>