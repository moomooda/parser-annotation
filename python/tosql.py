import sys
import uuid



def count_character(lines):
    count = 0
    for line in lines:
        items = line.strip().split("\t")
        count = count + len(items[1])

    return count

def has_eTag(lines):
    strr = ['ecoo','eequ','erect','esum','eselt','eaban','epref','eprec','ecau','econd','esupp','emetd','econc','eprog','eadvt','epurp','eresu','einf']
    # strr = ['eCoo','eEqu','eRect','eSum','eSelt','eAban','ePref','ePrec','eCau','eCond','eSupp','eMetd','eConc','eProg','eAdvt','ePurp','eResu','eInf']

    tag = 0
    for line in lines:
        items = line.strip().split("\t")
        if items[7].lower() in strr:
            tag = 1
            break
    return tag

def has_douhao(lines):
    tag = 0
    for line in lines:
        items = line.strip().split("\t")
        if items[1] == '，' or items[1] == ',':
            tag = 1
            break
    return tag

def elliptical(lines):
    tag = 0
    if has_eTag(lines) == 1 and has_douhao(lines) == 1:
        # 先找出所有逗号的位置	
        index_douhao= []
        for line in lines:
            items = line.strip().split("\t")
            if items[1] == '，' or items[1] == ',':
   	            index_douhao.append(items[0])
        
        strr = ['ecoo','eequ','erect','esum','eselt','eaban','epref','eprec','ecau','econd','esupp','emetd','econc','eprog','eadvt','epurp','eresu','einf']
        
        for line in lines:
            items = line.strip().split("\t")
            if items[7].lower() in strr:
                for index in index_douhao:
                    if index > min(items[0], items[6]) and index < max(items[0], items[6]):
                        tag = 1
                        # print(index)
                        # print(items[7])
                        break   
    return tag

def has_multiple_father(lines):
    flag = 0
    #  缺词问题
    l = len(lines) + 1
    record = [0]*l
    for line in lines:
        if line[0:1] == "#":
            print(line)
            continue
        try:
            items = line.strip().split("\t")
            index = int(items[0])
        # print("length of record ", len(record))
        # print("index ", index)
            record[index] += 1
        except IndexError:
            return 0
    for i in range(1,len(record)):
        if record[i] > 1:
            flag = 1
            break
    return flag

# 生成原始语料
# NOTE 多父节点会出现序号重复的单词
def create_original_corpus(sent):
    index_record = []
    sql = []
    for id, tok in enumerate(sent):
        index = tok[0]
        if index in index_record:
            continue
        else:
            index_record.append(index)
            sql.append('[' + str(index) + ']' + tok[1] + '/' + tok[2])
    return " ".join(sql)


# 生成关系语料
# 多父节点的出现需要另存序号和单词的对应关系
def create_relation(sent):
    # print(sent)
    index_record = []
    sql = []
    words = []

    for id, tok in enumerate(sent):
        index = tok[0]
        if index in index_record:
            continue
        else:
            index_record.append(index)
            words.append(tok[1])

    for id, tok in enumerate(sent):
        # print(sent)
        if id > 0:
            father = tok[3]
            child = tok[0]
            tag = tok[4]
            # print("index of father :", father)
            # print("index of child :", child)
            # print(words[int(father)])
            # print(words[int(child)])
            # print('len', len(words))
            sql.append('[' + str(father) + ']' + str(words[int(father)]) + '_[' + str(child) + ']' + str(words[int(child)]) + '(' + tag + ')')
    # print("\t\t".join(sql))
    return "\t\t".join(sql)

def output(sents,outfile,batch_id,user_id):
    sql = "INSERT INTO corpus(batch_id,import_date,`index`,status_id,user_id,original_corpus,relation) VALUES "
    i = 0
    raw_sent = create_original_corpus(sents[0])
    rels = create_relation(sents[0])

    sql += "('"+ str(batch_id) + "',NOW(),'"+str(i)+"','1','"+str(user_id)+"','"+ raw_sent + "','"  + rels + "')" # 一句即一个实体的各个属性值用括号括起来
    
    i += 1
    for sent in sents[1:]:
        try:
            raw_sent = create_original_corpus(sent)
            rels = create_relation(sent)
            sql += ",('"+ str(batch_id) + "',NOW(),'"+str(i)+"','1','"+str(user_id)+"','"+ raw_sent + "','"  + rels + "')" # 一句即一个实体的各个属性值用括号括起来
            i += 1
        except IndexError:
            continue
    fo = open(outfile, "w",encoding="utf")
    fo.write(sql)


if __name__ == "__main__":
    fi = open("fine_drama.txt", "r", encoding='utf-8')
    fi = fi.read()
    
    # sql文件名称
    name = "drama_cx"

    # 数据库字段参数
    batch_id = 46
    user_id = 7

    sents = fi.strip().split("\n\n")
    sentences = []
    n = 0

    for sent in sents:
        lines = sent.strip().split("\n")
        if len(lines) < 2:
            continue
        if elliptical(lines) == 1:
            sentences.append([])
            sentences[n].append((0,"Root", "Root", -1, "-NULL-"))
            for line in lines:
	            items = line.strip().split("\t")
	            if len(items) < 8:
	                continue
	            # 1   对   对   p   p   _   2   Nmod    2:Nmod|4:Agt    _
	            sentences[n].append((items[0], items[1], items[3], int(items[6]), items[7]))
            n += 1

    for i in range(1):
        output(sentences, name + ".sql", batch_id, user_id)
    print(n)
