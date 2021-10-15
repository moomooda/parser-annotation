import pymysql.cursors
import operator

def output(outfile, sents):
    fo = open(outfile, "w", encoding='utf')
    for sent in sents:
        for idx, tok in enumerate(sent):
            line = str(tok["idx"]) + "\t" + tok["tok"] + "\t" + tok["tok"] + "\t" + tok["pos"] + "\t" + tok[
                "pos"] + "\t_\t" + str(tok["hed"]) + "\t" + tok["rel"] + "\t_\t_\n"
            fo.write(line)
        fo.write("\n")

def process(infos):
    sents = []
    i = 0
    # print(len(infos))
    for info in infos:
        # print(i)
        sent = []
        sent2 = []
        word_numbers=[]
        i += 1
        for rel in info["result"]:
            if rel=="":
                pass
            else:
                for tok in info["tokens"]:
                    # print(tok)
                    # 当 tok 中带有/会解析出错
                    try:
                        token_with_pos = tok.split("]")[1]
                        seg_index = token_with_pos.rfind("/")
                        token = token_with_pos[0:seg_index]
                        pos = token_with_pos[seg_index+1:]
                        # print(token)
                        # print(pos)
                        if token == "Root":
                            pass
                        else:
                            # print(token)
                            sent.append({"tok": token, "pos": pos})  # [1]la/ws
                    except IndexError:
                        continue
                items = rel.split("_")
                # print(items)  # ['[0]Root', '[14]交谈(Root)'] ['[1]穿', '[3]衬衫(Pat)'
                index = int(items[1].split("]")[0].strip("["))  # index为 每行第 index个词的位置
                head = int(items[0].split("]")[0].strip("["))
                relation = items[1].split("(")[1].strip(")")
                word=items[1].split("(")[0].split("]")[1]
                # print(word,index, head, relation)  # 14 0 Root   3 1 Pat
                # print(word)
                word_numbers.append(index)
                for token in sent:
                    if token["tok"]==word:
                        # print(token)
                        sent2.append({"tok": word, "pos": token["pos"],"hed":head,"rel":relation,"idx":index})
                        # print(sent2)
                        break
        # print(word_numbers)
        if len(sent2)!=0:
            sorted_sent = sorted(sent2, key=operator.itemgetter('idx'))
            sents.append(sorted_sent)
        else:
            print(i)
    # print("sents_length:"+str(len(sents)))
    # print(sents[0])
    return sents

# 连接数据库
connect = pymysql.Connect(
    host = '175.24.14.16',
    port = 3306,
    user = 'root',
    passwd = 'blcu@246',
    db = 'annotation',
    charset = 'utf8'
)
# 获取游标
cursor = connect.cursor()
# 查询数据
sql = "SELECT original_corpus,relation FROM corpus WHERE batch_id = '18'"
cursor.execute(sql)
data = cursor.fetchall()
print('共查找出', cursor.rowcount, '条数据')

infos = []

for original_corpus,relation in data:
    info = {}
    info["tokens"] = original_corpus.strip().split()
    info["result"] = relation.strip().split("\t")
    infos.append(info)

sents = process(infos)
print('sents长度', len(sents))
output("法律.conll", sents)

# 关闭连接
cursor.close()
