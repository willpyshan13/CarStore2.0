<template>
    <div class="dashboard-container">

    </div>
</template>

<script>
export default {
    name: 'Dashboard',

    beforeCreate() {
        let accessedRoutes = this.$store.getters.accessedRoutes
        let path = accessedRoutes[0].path;
        this.$router.replace(path)
    }
}
</script>

<style lang="scss" scoped>
</style>
