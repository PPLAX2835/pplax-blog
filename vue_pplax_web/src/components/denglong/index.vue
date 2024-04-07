<template>
    <div class="app1" v-if="show">
        <div class="lantern-box">
            <div class="lantern-left">
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">新</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">年</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
            </div>
            <div class="lantern-right">
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">快</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">乐</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "lantern-app",
    props: {
        show: {
            type: Boolean,
            default: false
        }
    },
    methods: {

    },
    data() {
        return {}
    }
}



</script>

<style scoped></style>