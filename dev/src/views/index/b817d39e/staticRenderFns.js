var staticRenderFns = [{
                                                name: "groupId",
                                                label: "分组",
                                                type: "select",
                                                placeholder: "请选择分组",
                                                options: o.bookGroupSetList.map((function(t) {
                                                    return {
                                                        label: t.groupName,
                                                        value: t.groupId
                                                    }
                                                }))
                                            }];
