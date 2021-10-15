var segmentMenu = [{
        "text": "add	右侧增加一列",
        "events": {
            "click": function(e) {
                changeColumn(this.getElementsByTagName("span")[1].innerHTML);
            }
        }
    },
    {
        "text": "reduce	删除当前列",
        "events": {
            "click": function(e) {
                changeColumn(this.getElementsByTagName("span")[1].innerHTML);
            }
        }
    },
    {
        "text": "CTB词性",
        "sub": [{
                "text": "LC	定位词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
        },
        {
                "text": "P	介词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
        },
        {
                "text": "AD	副词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
        },
        {
                "text": "M	量词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
        },
        {
                "text": "PN	代词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
        },
        {
                "text": "连词",
                "sub":[{
                    "text": "CC	并列连词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "CS	从属连词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                }]       
        },
        {
                "text": "动词，形容词",
                "sub":[{
                    "text": "VA	表语形容词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "VC	系动词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "VE	“有”",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "VV	其他动词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                }
                ]     	
        },
        {
                "text": "名词",
                "sub":[{
                    "text": "NN	普通名词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "NR	专有名词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                },
                {
                    "text": "NT	时序词",
                    "events": {
                        "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                        }
                    }
                }]       
        },
        {
            "text": "限定词和数词",
            "sub":[{
                "text": "DT	限定词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "CD	数字",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "OD	序数词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            }]       
        },
        {
            "text": "助词",
            "sub":[{
                "text": "DEC	“的”词性标记",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "DEG	联结词“的”",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "DER	“得”",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "DEV	地",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "SP	句尾小品词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "AS	体态词，体标记",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "ETC	等，等等",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "MSP	例子",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            }]       
        },
        {
            "text": "其他",
            "sub":[{
                "text": "IJ	感叹词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": " ON	拟声词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "PU	标点",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "JJ	其他名词修饰语",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "FW	外来词",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "LB	长“被”结构",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {   
                "text": "SB	短“被”结构",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {   
                "text": "BA	把字结构",
                "events": {
                    "click": function(e) {
                    segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            ]       
        },        
        ]
    },
    {
        "text": "n系列",
        "sub": [{
                "text": "n	普通名词",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "nh	人名",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "ni	机构名",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "ns	地名(例如：北京)",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "nl	地点名词（例如：城郊）",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "nd	方位名词（例如：右侧）",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "nt	时间名词",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "nz	其他专有名词",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
        ]
    },
    {
        "text": "语素类",
        "sub": [{
                "text": "g	语素（例如：甥）",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "h	前缀",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "k	后缀",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            },
            {
                "text": "x	非成词语素（例如：葡）",
                "events": {
                    "click": function(e) {
                        segChangeTag(this.getElementsByTagName("span")[1].innerHTML)
                    }
                }
            }
        ]
    },
    {
        "text": "v	动词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "a	形容词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "d	副词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "m	数词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "p	介词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "c	连词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "b	区别词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "o	拟声词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "q	量词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "r	代词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "u	助词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "e	叹词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "j	缩略词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "i	成语俗语",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "ws 外来词",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    },
    {
        "text": "wp	标点符号",
        "events": {
            "click": function(e) {
                segChangeTag(this.getElementsByTagName("span")[1].innerHTML)

            }
        }
    }

]